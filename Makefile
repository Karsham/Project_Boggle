compil:
	javac -sourcepath sources -d classes sources/boggle/mots/ArbreLexical.java

exec:
	java -classpath classes boggle.mots.ArbreLexical


compilJeu:
	javac -sourcepath sources -d classes sources/boggle/BoggleGame.java

execJeu:
	java -classpath classes boggle.BoggleGame
