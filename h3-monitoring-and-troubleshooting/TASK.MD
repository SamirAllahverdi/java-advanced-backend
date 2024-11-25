- Which interface JDK tools use to connect to JVM locally?
  <br>JVMTI(native interface) and JMX

- What is difference between profiling and traceability?
  <br> Profiling is measuring CPU usage, memory consumption, etc.
  Traceability is tracking the flow of requests.

-----
- Observed heap-1.0.0-SNAPSHOT.jar heap consumption via jvisualvm

![Example Image](images/profilling-heap.jar.png)

-----
- Found the reason of leak in heap-1.0.0-SNAPSHOT.jar via jvisualvm
  <br>Limitless Strings were added to list in HeapMain.class

![Example Image](images/reason-for-memory-leak.png)

-----
- OQL queries were executed in jvisualvm

![Example Image](images/example_from_OQL.png)

-----
- Printed Thread.dump via jstack

- Remote JVM profilling via Jconsole

![Example Image](images/connect-to-jvm-via-jconsole.png)

-----
- Connected to Hotspot of JVM via JMC

![Example Image](images/connect-to-jvm-via-jmc.png)

-----
- Printed the properties via jinfo

![Example Image](images/jinfo-print-all-properties.png)

