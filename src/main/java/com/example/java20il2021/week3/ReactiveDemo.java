package com.example.java20il2021.week3;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *Observable.create(ObservableOnSubscribe)  
 * 		.map(x -> x);
 *
 * 	1.	v1 = new ObservableCreate(ObservableOnSubscribe)
 * 	2.	v1.map => new ObservableMap(this = v1,  x -> x) => v2
 * 	3.	v2.subscribe(My-Observer)
 * 	        a. v2(ObservableMap).subscribeActual
 * 	        b. v2.source.subscribe(o2 = new Observer(My-Observer, x -> x));
 *
 * c1 => Emitter.subscribe(o1)
 * m1 => c1 .subscribe(o1 = (o2 (My-Observer, m2-lambda), m1-lambda))
 * m2 => m1.subscribe(o2 = (My-Observer, m2-lambda))
 *
 * ObservableOnSubscribe.subscribe(emitter(o1)) {
 * 	    emitter.onNext(“str”) {
 * 	        o1.onNext(“str”) {
 * 	            v = m1-lambda(“str”);
 * 	            o2.onNext(v) {
 * 	                z = m2-lambda(v);
 * 	                My-Observer.onNext(z) {
 * 	                    ...
 *                  }
 *              }
 *          }
 *      }
 * }
 */

public class ReactiveDemo {

    public static void main(String[] args) throws InterruptedException {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("Observable subscribe() 所在线程为 :" +  Thread.currentThread().getName());
                emitter.onNext("文章1");
                emitter.onNext("文章2");
                emitter.onComplete();
            }
        })
                .map(x -> x)
                .map(x -> x)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("Observer onSubscribe() 所在线程为 :" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s + "..." + "Observer onNext() 所在线程为 :" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Observer onError() 所在线程为 :" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Observer onComplete() 所在线程为 :" + Thread.currentThread().getName());
                    }
                });
        Thread.sleep(5000);
    }
}
