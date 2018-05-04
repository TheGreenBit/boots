package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.*;
import com.bird.elasticsearch.repository.GoodsRepository;
import com.google.inject.internal.util.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GoodsSearchServiceImpl implements GoodsSearchService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ConcurrentMap<Integer, CarType> carType = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, Area> areas = new ConcurrentHashMap<>();


    private Boolean indexGoods(int i) {
        List<Goods> list = searchGoods(i, 100);
        System.out.println("sql page:" + i + "\t" + 100 + "\t query size" + (list == null ? 0 : list.size()));
        if (list != null && list.size() > 0) {
            doIndexGoods(list);
            return true;
        }
        return false;
    }

    final AtomicInteger start = new AtomicInteger(0);
    private AtomicInteger indexErr = new AtomicInteger(0);

    public void doIndex() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Callable<Boolean>> task = null;
        boolean flag = true;
        while (flag) {
            task = newTask(4);
            try {
                List<Future<Boolean>> result = executorService.invokeAll(task);
                flag = checkTask(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
                flag = !(indexErr.incrementAndGet() > 10);
            }

        }

        System.out.println("一共索引商品数据" + start.get());
    }


    public void doIndexDeque() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        final Check check = new Check();
        Callable<Boolean> producer = () -> searchAndPush(check);
        //Callable<Boolean> consumer = () -> pollAndIndex(check);
        ;
        //3个线程进行查询 并放入队列
        long start = System.currentTimeMillis();
        executorService.submit(producer);
        executorService.submit(producer);
        executorService.submit(producer);

        pollAndIndex(check);

        System.out.println("execute time:" + (System.currentTimeMillis() - start));
    }

    private boolean searchAndPush(Check check) {
        while (true && !check.end) {
            List<Goods> list = searchGoods(check.page.getAndAdd(400), 400);
            check.push(list);
        }

        System.out.println(Thread.currentThread() + "搜索到尽头了。。。" + check.page.get());
        return true;
    }

    private boolean pollAndIndex(Check check) {
        while (!check.end || !check.noTask()) {
            List<Goods> poll = check.poll();
            doIndexGoods(poll);
        }
        System.out.println("poll no tasks");
        return true;
    }

    class Check {
        volatile boolean end = false;
        AtomicInteger indexCount = new AtomicInteger(0);
        AtomicInteger page = new AtomicInteger(0);
        Queue<List<Goods>> deque = new ConcurrentLinkedQueue();

        void add(int v) {
            indexCount.addAndGet(v);
        }

        int get() {
            return indexCount.get();
        }

        void check(List list) {
            this.end = (list.size() == 0);
        }

        boolean noTask() {
            return deque.isEmpty();
        }

        boolean push(List<Goods> gd) {
            boolean flag = true;
            if (gd.size() > 0) {
                deque.offer(gd);
                System.out.println("push task for index...");
            } else {
                this.end = true;
                flag = false;
            }
            return flag;
        }

        List<Goods> poll() {
            System.out.println("pool task for index...");
            return deque.poll();
        }

    }

    public long queryGoodsCount() {
        List<Long> query = jdbcTemplate.query("SELECT COUNT(1) FROM b_goods", new SingleColumnRowMapper<>(Long.TYPE));
        Long aLong = query.get(0);
        return aLong == null ? 0 : aLong.longValue();
    }

    public void doIndexQuickly() {
        ExecutorService scheduledExecutorService = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors());
        List<Long> query = jdbcTemplate.query("SELECT COUNT(1) FROM b_goods", new SingleColumnRowMapper<>(Long.TYPE));
        Long aLong = query.get(0);
        Objects.requireNonNull(aLong);
        //查询商品总数
        AtomicInteger atomicInteger = new AtomicInteger(0);


        System.out.println("start goods searched count:" + aLong);

        long l = System.currentTimeMillis();
        Check check = new Check();
        while (aLong.intValue() - atomicInteger.get() > 0) {
            scheduledExecutorService.execute(() -> {
                List<Goods> goods = searchGoods(atomicInteger.getAndAdd(100), 100);
                doIndexGoods(goods);
                check.add(goods.size());
                check.check(goods);
                System.out.println(check.get());
            });
        }


        while (!check.end) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("going end " + (System.currentTimeMillis() - l));
        try {
            Thread.sleep(4000);
            System.out.println("execute index time:" + (System.currentTimeMillis() - l) + "index items:" + check.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doIndexGoods(List<Goods> list) {
        if(list!=null) {
            List<GoodsData> collect = list.parallelStream().map(goods -> GoodsData.of(goods)).collect(Collectors.toList());
            goodsRepository.saveAll(collect);
        }
    }

    @Override
    public List<GoodsData> search(GoodsQuery goodsQuery) {
        BoolQueryBuilder b = QueryBuilders.boolQuery();
        b.must().addAll(goodsQuery.getQueryBuilders());
        //b.filter(QueryBuilders.rangeQuery(RangeQueryBuilder.NAME).from(0).to(15));
        Iterable<GoodsData> search = goodsRepository.search(b, PageRequest.of(1, 12));
        return search == null ? null : Lists.newArrayList(search);
    }

    private List<Callable<Boolean>> newTask(int s) {
        ArrayList<Callable<Boolean>> objects = new ArrayList<>(s);
        for (int i = 0; i < s; i++) {
            objects.add(() -> indexGoods(start.getAndAdd(100)));
        }
        return objects;
    }

    private boolean checkTask(List<Future<Boolean>> tasks) {
        return tasks.stream().map(a -> {
            try {
                return a.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return true;
        }).reduce(false, (a, b) -> a || b).booleanValue();

    }

    private void loadCarType(BaseGoods goods) {
        List<CarType> query = jdbcTemplate.query(carTypesSql(goods.getGoodsId()), new BeanPropertyRowMapper<>(CarType.class));
        if (query != null && query.size() > 0) {
            query.stream().forEach(q -> goods.addCarInfo(q));
        }
    }

    private String carTypesSql(Long gid) {
        return "select * from b_goods_cartype g inner join a_cartype  a on a.carTypeId=g.carTypeId where goodsId= " + gid;
    }

    private void loadCityInfo(BaseGoods goods) {
        List<Area> query = jdbcTemplate.query("select * from u_county where countyId=" + goods.getCountyId() + " limit 1", new BeanPropertyRowMapper<>(Area.class));
        if (query != null && query.size() > 0) {
            Area area = query.get(0);
            goods.setCityName(area.getCity());
        }
    }

    private List<Goods> searchGoods(int i, int s) {
        String s1 = goodsSql(i, s);
        System.out.println(s1);
        List<Goods> goods = jdbcTemplate.query(s1, new BeanPropertyRowMapper(Goods.class));
        if (goods == null || goods.size() == 0) {
            return Collections.emptyList();
        }
        goods.forEach(gd -> {
            loadCarType(gd);
            loadCityInfo(gd);
        });
        return goods;
    }

    private String goodsSql(int i, int s) {
        return "SELECT g.goodsId," +
                " g.shopId," +
                " g.goodsName," +
                " g.brandCode," +
                " g.brandNO," +
                " g.brandName," +
                " g.allCarType," +
                " g.goodsStyle," +
                " s.countyId," +
                " s.shopName," +
                " s.shopType," +
                " s.ImageUrl shopImage," +
                " s.latitude ," +
                " s.cityId ," +
                " s.countyId ," +
                " s.longitude ," +
                " g.carType," +
                " bs.typeName," +
                " g.goodsSysTypeId," +
                " b.brandId," +
                " b.brandName," +
                " g.goodsTypeId," +
                " g.stock," +
                " g.quality," +
                " g.unit," +
                " g.place," +
                " g.price," +
                " g.fineness," +
                " g.imageUrl," +
                " g.sales," +
                " g.shelfLife," +
                " g.warrantyYear," +
                " g.warrantyMail," +
                " g.content," +
                " g.state," +
                " gb.brow," +
                " g.createTime" +
                " FROM b_goods g LEFT JOIN b_shop s ON s.shopId=g.shopId " +
                " LEFT JOIN b_goods_brand bc ON bc.goodsId=g.goodsId " +
                " LEFT JOIN a_brand b ON bc.brandId=b.brandId " +
                " LEFT JOIN b_goods_sys_type bs ON g.goodsSysTypeId=bs.typeId" +
                " LEFT JOIN b_goods_brow gb ON g.goodsId = gb.goodsId limit " + i + "," + s;
    }

}
