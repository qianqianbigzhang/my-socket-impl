#!/bin/sh

#===========================================================================================
# Java 环境设置
#===========================================================================================
error_exit ()
{
    echo $1
    exit 1
}

success_exit ()
{
    echo $1
    exit 0
}

[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=$HOME/jdk/java
[ ! -e "$JAVA_HOME/bin/java" ] && JAVA_HOME=/usr/lib/jvm/java-1.7.0-openjdk.x86_64/
[ ! -e "$JAVA_HOME/bin/java" ] && error_exit "Please set the JAVA_HOME variable in your environment, We need java(x64)!"

BASE_DIR=$(dirname $0)

JAVA="$JAVA_HOME/bin/java"
export CLASSPATH=./:${CLASSPATH}

#===========================================================================================
# 系统环境
#===========================================================================================
JARFILE=${BASE_DIR}/lib/my-socket-simple-1.0.0.jar
MPSPIDFILE=${BASE_DIR}/appmps.pid


#===========================================================================================
# JVM 参数配置
#===========================================================================================
JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g"
JAVA_OPT="${JAVA_OPT} -DCorePath=/home/appmps/ins1/my-socket-simple/conf/core.properties"
JAVA_OPT="${JAVA_OPT} -Dcom.bigzhang.log.level=INFO"
#JAVA_OPT="${JAVA_OPT} -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8 -XX:+DisableExplicitGC"
#JAVA_OPT="${JAVA_OPT} -verbose:gc -Xloggc:${HOME}/daemon_bk_gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
#JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow"
#JAVA_OPT="${JAVA_OPT} -Djava.ext.dirs=${BASE_DIR}/lib"
#JAVA_OPT="${JAVA_OPT} -Xdebug -Xrunjdwp:transport=dt_socket,address=9555,server=y,suspend=n"
#JAVA_OPT="${JAVA_OPT} -cp ${CLASSPATH}"

case $1 in
start)
    echo "服务启动 ... "
    if [ -f "$MPSPIDFILE" ]; then
      if kill -0 `cat "$MPSPIDFILE"` > /dev/null 2>&1; then
         success_exit 在运行中，pid为： `cat "$MPSPIDFILE"`.
      fi
    fi

    nohup $JAVA $JAVA_OPT -jar $JARFILE > appmps.out 2>&1 < /dev/null &

    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$MPSPIDFILE"
      then
        sleep 1
        echo 服务已启动
      else
        error_exit 往PID文件写入失败
      fi
    else
      error_exit 服务没有启动成功
    fi
    ;;
stop)
    echo "服务关闭 ... "
    if [ ! -f "$MPSPIDFILE" ]
    then
      echo "没有服务要关闭(找不到文件 $MPSPIDFILE)"
    else
      kill $(cat "$MPSPIDFILE")
      rm "$MPSPIDFILE"
      echo 服务停止
    fi
    exit 0
    ;;
restart)
    shift
    "$0" stop
    sleep 3
    "$0" start
    ;;
status)
    echo 运行中 ...
    ;;
*)
    echo   "Usage {start|stop|restart|status}"
esac

