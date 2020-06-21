## ****项目搭建****
项目采用MVP开发模式,网络请求框架Retrofit+RxJava，使用Lifecycle监听Activity生命周期的变化
## 特点
1.将Control层与Model层进行解耦
2.可以在需要的时候取消网络数据请求
3.接口调用简单方便
## 说明
定义接口
```java
    public interface FlowService {
        @GET("/api/action/datastore_search")
        Observable<Result<FlowResult>> getFlow(@Query("resource_id") String resourceId,@Query("offset") int offset, @Query("limit") int limit);
    }
```
接口使用
```java
    /**
     * 获取数据量
     *
     * @param resourceId
     * @param offset
     * @param limit
     */
    public void getFlows(String resourceId, int offset, int limit) {
        ProgressSubscriber flowSubscriber = new ProgressSubscriber<Result<FlowResult>>(mView) {
            @Override
            public void onNext(Result<FlowResult> data) {
                if (mView != null) {
                    mView.loadData(data.getResult());
                }
            }
        };
        mService.getFlow(resourceId, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(flowSubscriber);
        addSubscriber(flowSubscriber);
    }
```
http单例
```java
public class Http {

    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;

    //构造方法私有
    private Http() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Configs.base_url)
                .build();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Http INSTANCE = new Http();
    }

    //获取单例
    public static Http getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```使用Gson进行数据的解析在retrofit请求返回之前进行拦截解析
```java
```java
class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        Log.d("Network", "response>>" + response);
        return gson.fromJson(response, type);
    }
}
```
对返回数据的异常进行处理
```java
public class ApiException extends RuntimeException {


    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        return null;
    }
}
```
数据请求进行流程进行监听
```java
/**
 * 用于在Http请求开始时，自动回调开始请求
 * 在Http请求结束是，自动回调请求结束
 * 调用者自己对请求数据进行处理
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private IView iView;

    public ProgressSubscriber(IView view) {
        this.iView = view;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if (iView != null) {
            iView.startLoadding();
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        if (iView != null) {
            iView.finishLoadding();
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException && iView != null) {
            Log.e(getClass().getSimpleName(),"网络中断，请检查您的网络状态");
            Toast.makeText(iView.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Log.e(getClass().getSimpleName(),"网络中断，请检查您的网络状态");
            Toast.makeText(iView.getContext(), "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(getClass().getSimpleName(),e.getMessage());
            Toast.makeText(iView.getContext(), "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
```
