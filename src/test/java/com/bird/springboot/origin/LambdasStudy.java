package com.bird.springboot.origin;

import com.bird.config.Album;
import com.bird.config.Musician;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class LambdasStudy {

    private List<Album> albums = new ArrayList<>();
    private List<Musician> musicians = new ArrayList<>();

    @Before
    public void init() {
        Musician dolphin = new Musician("dolphin","Australia",21);
        Musician elephant = new Musician("elephant","India",21);
        Musician crane = new Musician("crane","China",25);

        Album fly = new Album("fly away!",crane,99);
        Album swim = new Album("swim away!",dolphin,53);
        Album walk = new Album("walk away!",elephant,54);
        Album hide = new Album("hide away!",crane,54);

        albums.addAll(Arrays.asList(fly,swim,walk,hide));
        musicians.addAll(Arrays.asList(dolphin,elephant,crane));
    }

    @Test
    public void streamTest() {
        albums.stream().filter(a->a.getMinutes()>52).
                sorted(comparing(a->a.getTitle()))
                .map(a->a.getAuthor()).forEach(a-> System.out.println(a));
    }

    @Test
    public void testStream() {
        Set<Integer> collect = Stream.of(56, 88, 77).map(a -> a + 2).filter(a -> a % 2 == 0).collect(toSet());
        System.out.println(collect);
    }

    @Test
    public void testReduce() {
        Integer result = Stream.of(1,2,3,4,5).sorted()
                .reduce(3,(a,b)->a+b);
        System.out.println(result);
    }

    @Test
    public void testRecur() {
        /*Optional<Album> album = albumOptional(albums.stream());
        System.out.println(album.get());*/
        albumGroupBy(albums.stream());
    }

    private Optional<Album> albumOptional(Stream<Album> albumStream){
        Function<Album,Long> gc = album->album.getMinutes();
        return albumStream.collect(maxBy(comparing(gc)));
    }

    private void albumPart(Stream<Album> albumStream){
        Map<Boolean, List<Album>> collect = albumStream.collect(partitioningBy(a -> a.getMinutes() > 78));
        System.out.println(collect.get(Boolean.TRUE));
        System.out.println(collect.get(Boolean.FALSE));
    }

    private void albumGroupBy(Stream<Album> albumStream){
        Map<String, List<Album>> collect = albumStream.collect(groupingBy(a -> a.getAuthor().getName()));
        collect.forEach((a,b)-> System.out.println(a+"\t"+b));
    }

    @Test
    public void stringStream() {
        String collect = albums.stream().map(a -> a.getTitle()).collect(joining(",", "[", "]"));
        System.out.println(collect);
    }

    @Test
    public void testSj() {
        String today = new StringJoiner("today").add(" is ")
                .add(" very").merge(new StringJoiner("nice!").add(" bird")).toString();
        System.out.println(today);
    }

    public <R> R go(R origin,Recursion<R> recursion){
        return recursion.recursion(origin);

    }

    @FunctionalInterface
    public interface Recursion<R> {

       default R recursion(R f){
           Returnable<R> recursion = exe(f);
           if(recursion.canReturn()) {
               return recursion.result();
           }
           return recursion(recursion.result);

       }

        <R> Returnable<R> exe(R f);

    }

    @FunctionalInterface
    public interface Execute<R> {
        <R> Returnable<R> exe(R f);
    }

    public static class Returnable<T>{
        private boolean canReturn;
        private T result;

        public Returnable(boolean canReturn, T result) {
            this.canReturn = canReturn;
            this.result = result;
        }

        public boolean canReturn(){
            return canReturn;
        }

        public T result(){
            return result;
        }

        public static <T> Returnable<T> of(boolean canReturn,T t){
            return new Returnable<>(canReturn,t);
        }

        public Returnable<T> replace(boolean flag,T result){
            this.canReturn = flag;
            this.result = result;
            return this;
        }
    }
}