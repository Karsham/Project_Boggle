compil:
	javac -sourcepath sources -d classes sources/boggle/BoggleGame.java

exec:
	java -classpath classes boggle.BoggleGame

doc:
	mkdir -p docs/;
	javadoc -author -private -sourcepath ./sources sources/boggle/*.java -d docs
