# j2v8分支

## ViewBinding
https://developer.android.google.cn/topic/libraries/view-binding


入门指南:https://eclipsesource.com/blogs/tutorials/getting-started-with-j2v8/

参考博客：
https://blog.csdn.net/heqiangflytosky/article/details/76832673


```
v8.executeVoidScript("function myFunc(params) { var myDate = new Date(params.timestamp); return myDate; }");

V8Value jsParams = V8ObjectUtils.toV8Object(v8, new HashMap<String, Object>() { {
  put("timestamp", new Date().getTime());
} });

V8Array parameters = new V8Array(v8);
parameters.push(jsParams);

Object result = v8.executeFunction("myFunc", parameters);

System.out.println(result);
```

```
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

public class Main {
	public static void main(String[] args) {

		V8 runtime = V8.createV8Runtime();
		new MyClass().initialize(runtime);

		runtime.executeVoidScript("var myObj = new MyClass();"
				+ "myObj.helloWorld();"
				+ "myObj.print('foo');");
	}
}

class MyClass {

	public void initialize(V8 runtime) {
		runtime.registerJavaMethod(this, "myClassJsConstructor", "MyClass", new Class<?>[] { V8Object.class }, true);

		V8Object obj = runtime.getObject("MyClass");
		V8Object prototype = runtime.executeObjectScript("MyClass.prototype");

		prototype.registerJavaMethod(this, "helloWorld", "helloWorld", null);
		prototype.registerJavaMethod(this, "print", "print", new Class<?>[] { String.class });

		obj.setPrototype(prototype);
	}

	public void helloWorld() {
		System.out.println("Hello world!");
	}

	public void print(String message) {
		System.out.println(message);
	}

	public void myClassJsConstructor(V8Object obj) {}
}
```