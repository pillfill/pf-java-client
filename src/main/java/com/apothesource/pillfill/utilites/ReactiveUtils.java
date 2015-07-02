/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apothesource.pillfill.utilites;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 * @author Apothesource, Inc
 */
public class ReactiveUtils {
    /**
     * A RxJava transformer to push processing events onto the IO thread and results onto the Immediate thread.
     * @param <T> The object type to return
     * @return A transformer to be applied to any existing observable
     */
    public static <T> Observable.Transformer<T, T> transformSchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate());
    }
    
    public static <T> Observable<T> subscribeIoObserveImmediate(Observable<T> obs){
        return obs.compose(transformSchedulers());
    }
    
    public static <T> Observable<T> subscribeIoObserveImmediate(Observable.OnSubscribe<T> obs){
        return subscribeIoObserveImmediate(Observable.create(obs));
    }
}
