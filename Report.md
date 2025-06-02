# Theoretical Questions – Report.md

## 1. start() vs run()

### What output do you get from the program? Why?

When we run this program, the output looks something like this:

```
Calling run()
Running in: main
Calling start()
Running in: Thread-2
```

This happens because when we call `t1.run()`, it just runs the code inside the `run()` method on the **main thread**, like calling a regular method. That's why it prints `Running in: main`.

But when we use `t2.start()`, it creates a **new thread** and then runs the `run()` method on that thread. So it prints `Running in: Thread-2`, which is the name we gave that thread.

### What’s the difference in behavior between calling start() and run()?

The key difference is that `start()` actually creates a new thread and runs `run()` inside that new thread. This lets your program do multiple things at once (which is the whole point of multithreading).

On the other hand, `run()` is just like calling a regular method. It doesn’t create a new thread; it runs in the current thread, so no parallel execution happens.

So if you want true multithreading behavior, you should always use `start()`.

---

## 2. Daemon Threads

### What output do you get from the program? Why?

You’ll likely see:

```
Main thread ends.
```

and maybe one or two lines of:

```
Daemon thread running...
```

But the important thing is that the daemon thread doesn’t get to finish its loop. That’s because once the **main thread** ends, the whole program exits—even if daemon threads are still running. Daemon threads are meant to do background stuff, but they don’t keep the program alive.

### What happens if you remove thread.setDaemon(true)?

If you remove that line, the thread becomes a **user thread** instead of a daemon. That means the program **will not exit** until this thread finishes its work. So now, the program will stay alive long enough for the loop to print all 20 lines of “Daemon thread running...”

### What are some real-life use cases of daemon threads?

Daemon threads are good for things that should run in the background but aren’t essential. For example:

* Garbage collection
* Background logging
* Monitoring tasks

If they don’t finish, it’s not the end of the world. That’s why Java doesn’t wait for them when exiting.

---

## 3. A shorter way to create threads

### What output do you get from the program?

You’ll see:

```
Thread is running using a ...!
```

### What is the `() -> { ... }` syntax called?

That’s a **lambda expression**. It’s a shorter way of writing something that implements a functional interface, like `Runnable`.

### How is this code different from creating a class that extends Thread or implements Runnable?

Normally, if you want to create a thread, you'd either:

* Create a new class that extends `Thread`, or
* Create a class that implements `Runnable`.

Both options take more lines and require extra boilerplate.

With a lambda expression, you can define the `run()` method in just one line, right where you create the thread. It’s faster, cleaner, and easier to read—especially when you don’t need to reuse the logic anywhere else.

So overall, it’s just a more modern and compact way to create threads for simple tasks.
