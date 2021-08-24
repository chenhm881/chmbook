package com.chm.book.article.command;

import com.chm.book.article.domain.TBlog;
import com.chm.book.article.service.BlogService;
import com.netflix.hystrix.*;
import lombok.Getter;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;

import java.util.ArrayList;
import java.util.List;

public class BlogHystrixObservableCommand extends HystrixObservableCommand<List<TBlog>> {

    @lombok.Setter
    @Getter
    private Integer id;

    private BlogService blogService;

    public BlogHystrixObservableCommand(BlogService blogService) {
        super(HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("blogService1"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("getTBlog1"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(10)
                .withCircuitBreakerSleepWindowInMilliseconds(50000)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(3))
              );
        this.blogService = blogService;
    }

    @Override
    protected Observable<List<TBlog>> construct() {

        return Observable.create(AsyncOnSubscribe.createStateful(
                new Func0<List<TBlog>>() {
                    @Override
                    public List<TBlog> call() {
                        List<TBlog> tBlogList = blogService.getTBlog(id);
                        return tBlogList;
                    }
                }, new Func3<List<TBlog>, Long, Observer<Observable<? extends List<TBlog>>>, List<TBlog>>() {
                    @Override
                    public List<TBlog> call(List<TBlog> tBlogList, Long aLong, Observer<Observable<? extends List<TBlog>>> observableObserver) {
                        Observable<? extends List<TBlog>> listObservable = Observable.just(tBlogList);
                        observableObserver.onNext(listObservable);
                        observableObserver.onCompleted();
                        return tBlogList;
                    }
                },
                new Action1<List<TBlog>>() {
                    @Override
                    public void call(List<TBlog> tBlogList) {
                          Object o = tBlogList.get(0);
                    }
                }
        ));

    }

    @Override
    public Observable<List<TBlog>> resumeWithFallback() {

        return Observable.create(SyncOnSubscribe.createStateful(
                new Func0<Integer>() {
                    @Override
                    public Integer call() {
                        return id + 1;
                    }
                },
                new Func2<Integer, Observer<? super List<TBlog>>, Integer>() {
                    @Override
                    public Integer call(Integer integer, Observer<? super List<TBlog>> observer) {
                        try {
                            List<TBlog> tBlogList = new ArrayList<>();
                            observer.onCompleted();
                            observer.onNext(tBlogList);
                        } catch (Exception error) {
                            observer.onError(error);
                        }
                        return integer + 1;
                    }
                },
                new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Integer i = integer + 1;
                    }
                })
        );
    }
}
