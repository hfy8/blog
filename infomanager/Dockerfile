#From java image, version : 8
# 指定镜像，这里用网易云仓库的java8镜像
FROM hub.c.163.com/library/java:8-jre
#FROM java:openjdk-8-alpine

# 挂载test-docker目录,创建/tmp目录并持久化到Docker数据文件夹，
# 因为Spring Boot使用的内嵌Tomcat容器默认使用/tmp作为工作目录。
VOLUME /tmp

# COPY or ADD to image
# 将编译目标jar包复制到/app.jar
ADD /build/libs/infomanager-0.0.1-SNAPSHOT.jar app.jar

# 默认8080端口
# 指定项目暴露的端口号，与项目配置一样,即容器配置的暴露端口
EXPOSE 8085
#ENTRYPOINT 执行项目 app.jar。为了缩短 Tomcat 启动时间，添加一个系统属性指向 "/dev/urandom" 作为 Entropy Source
ENTRYPOINT ["java", "-Xmx258m","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8085", "/app.jar"]
