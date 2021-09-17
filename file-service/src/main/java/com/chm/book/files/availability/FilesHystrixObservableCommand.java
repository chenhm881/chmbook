package com.chm.book.files.availability;

import com.chm.book.files.domain.FileScanResponse;
import com.chm.book.files.service.FileScanExecute;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.observables.SyncOnSubscribe;
import java.util.List;

public class FilesHystrixObservableCommand extends HystrixObservableCommand<FileScanResponse> {

    private Integer projectId;
    private List<String> locations;

    private FileScanExecute fileScanExecute;

    public FilesHystrixObservableCommand(FileScanExecute fileScanExecute, Integer projectId, List<String> locations) {
        super(HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("fileScanService"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("addFiles"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(10)
                .withCircuitBreakerSleepWindowInMilliseconds(50000)
                .withCircuitBreakerErrorThresholdPercentage(50)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                .withExecutionIsolationSemaphoreMaxConcurrentRequests(3))
              );
        this.fileScanExecute = fileScanExecute;
        this.projectId = projectId;
        this.locations = locations;
    }

    @Override
    protected Observable<FileScanResponse> construct() {
        return Observable.create(SyncOnSubscribe.createStateless(
                new Action1<Observer<? super FileScanResponse>>() {
                    @Override
                    public void call(Observer<? super FileScanResponse> observer) {
                        try {
                            locations.stream().forEach(
                                    location -> {
                                        FileScanResponse fileScanResponse = fileScanExecute.execute(projectId, location, "add");
                                        observer.onNext(fileScanResponse);
                                    }
                            );
                            observer.onCompleted();
                        } catch (Exception ex) {
                            observer.onError(ex);
                        }
                    }
                }));
    }

    @Override
    protected  Observable<FileScanResponse> resumeWithFallback() {
        return Observable.create(SyncOnSubscribe.createStateless(
                new Action1<Observer<? super FileScanResponse>>() {
                    @Override
                    public void call(Observer<? super FileScanResponse> observer) {
                        try {
                            locations.stream().forEach(
                                    location -> {
                                        FileScanResponse fileScanResponse = new FileScanResponse();
                                        observer.onNext(fileScanResponse);
                                    }
                            );
                            observer.onCompleted();
                        } catch (Exception ex) {
                            observer.onError(ex);
                        }
                    }
                }));
    }
}
