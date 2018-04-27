package com.bird.elasticsearch.service;

import com.bird.elasticsearch.beans.Area;
import com.bird.elasticsearch.beans.CarType;
import com.bird.elasticsearch.beans.Goods;
import com.bird.elasticsearch.beans.GoodsQuery;
import com.bird.elasticsearch.repository.GoodsRepository;
import com.google.inject.internal.util.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
            list.parallelStream().forEach(a -> {
                loadCarType(a);
                loadCityInfo(a);
            });
            goodsRepository.saveAll(list);
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

    @Override
    public List<Goods> search(GoodsQuery goodsQuery) {
        BoolQueryBuilder b = QueryBuilders.boolQuery();
        b.must().addAll(goodsQuery.getQueryBuilders());
        //b.filter(QueryBuilders.rangeQuery(RangeQueryBuilder.NAME).from(0).to(15));
        Iterable<Goods> search = goodsRepository.search(b, PageRequest.of(1, 12));
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
        /*boolean flag = false;
        if (tasks != null && tasks.size() > 0) {
            for (Future<Boolean> future : tasks) {
                try {
                    flag = flag || future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;*/
    }

    private void loadCarType(Goods goods) {
        List<CarType> query = jdbcTemplate.query(carTypesSql(goods.getGoodsId()), new BeanPropertyRowMapper<>(CarType.class));
        if (query != null && query.size() > 0) {
            query.stream().forEach(q -> goods.addCarInfo(q));
        }
    }

    private String carTypesSql(Long gid) {
        return "select * from b_goods_cartype g inner join a_cartype  a on a.carTypeId=g.carTypeId where goodsId= " + gid;
    }

    private void loadCityInfo(Goods goods) {
        List<Area> query = jdbcTemplate.query("select * from u_county where countyId=" + goods.getCountyId() + " limit 1", new BeanPropertyRowMapper<>(Area.class));
        if (query != null && query.size() > 0) {
            Area area = query.get(0);
            goods.setCityName(area.getCity());
        }
    }

    private List<Goods> searchGoods(int i, int s) {
        String s1 = goodsSql(i, s);
        System.out.println(s1);
        return jdbcTemplate.query(s1, new BeanPropertyRowMapper(Goods.class));
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
