@echo off
rem PAUSE

if "%APP_HOME%"=="" (
	echo "<<<<<<<<<<<ERROR: ����ȷ����[APP_HOME]������ִ��app.bat<<<<<<<<<<<<<"
	PAUSE
	exit
)

if "%APP_MAINCLASS%"=="" (
	echo "<<<<<<<<<<<ERROR: ����ȷ����[APP_MAINCLASS]������ִ��app.bat<<<<<<<<<<<<<"
	PAUSE
	exit
)

rem �л�����Ϊutf-8,������־����
chcp 65001

rem ָ��cmd��������
title %WINDOW_TITLE%

rem java������������� -Xms����ʼֵ -Xmx�����ֵ  -Xmn�������������Ĵ�С
set JAVA_OPTS=-Xms512m -Xmx1024m -Xmn128m -Djava.awt.headless=true -XX:MaxPermSize=64m

rem ָ��CLASSPATH
rem CLASSPATH����ָ��̫��jar�ļ�����֡�������̫�������Ĵ��󡣽���취Ϊֱ��ָ����libĿ¼ͨ�� \*ָ������jar
set CLASSPATH=%APP_HOME%\classes;%APP_HOME%\lib\*

goto doStart

:doStart
java %JAVA_OPTS% -classpath "%CLASSPATH%" %APP_MAINCLASS%
:end

PAUSE
exit