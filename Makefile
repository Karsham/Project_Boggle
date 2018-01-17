compil:
	javac -sourcepath sources -d classes sources/boggle/BoggleGame.java

exec:
	java -classpath classes boggle.BoggleGame

doc:
	mkdir -p docs/;
	javadoc -d docs -author -private -sourcepath ./sources sources/boggle/*.java -subpackages boggle.jeu boggle.mots boggle.ui
