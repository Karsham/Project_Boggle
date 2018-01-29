compil:
	./projetcomp
	javac -sourcepath sources -d classes sources/boggle/BoggleGame.java

exec:
	java -jar Prog.jar

doc:
	mkdir -p docs/;
	javadoc -d docs -author -private -sourcepath ./sources sources/boggle/*.java -subpackages boggle.jeu boggle.mots boggle.ui
