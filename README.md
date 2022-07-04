# MyRPC

参考 ：https://github.com/he2121/MyRPCFromZero

从零开始完成一个RPC框架

version0 : 客户端与服务端之间的通讯通过Socket编程的方式实现，服务端使用BIO的方式监听特定端口，数据通过Java自带的流的序列化方式传输

version1 : 在前一版本的基础上定义统一的消息格式，并加入动态代理和反射使得服务端可以调用一个服务中的多个方法

version2 : 服务端加入线程池，进一步优化BIO方式的性能。并引入HashMap作为服务的注册中心，使得客户端可以访问不同的服务

version3 : 重构代码，引入Netty框架，通过NIO的方式完成通信

version4 : 在NIO的基础上，实现了自定义的序列化方式（Json)。相对于原生的序列化方式提升了性能，并且通过自定义编码器和解码器解决了数据粘包的问题

version5 : 引入ZooKeeper作为正式的服务注册中心。代码中通过Curator库来操作zookeeper客户端。实现了完整的RPC中的三个角色：服务提供者，服务消费者，注册中心

version6 : 引入负载均衡策略，实现了2种策略（随机和轮询）。使得ZooKeeper可以根据负载均衡策略返回适合的服务器地址
