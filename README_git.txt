note git club info

# Initialiser un repo : 			git clone [url du repo] ou git init [nom du projet]
# Info sur le repo : 				git status
# Lister des commits : 				git log
# Voir les modifications non enregistr�es : 	git diff (--cached)

Proc�dures :

Pousser ses modifications locales sur le repo online et recuperer le travail des autres

=> git add [nom des fichiers s�par�s par un espace]
	Pour regrouper les fichiers modifi�s dans le commit
		Rq : pour ajouter automatiquement tous les fichiers modifi�s vous pouvez mettre "." au lieu des noms des 		fichiers, mais c'est plus maitriser ce que l'on fait de les ajouter � la main
=> git checkout [nom de la branche]
	Pour se positionner sur la branche qui vous est attribu�e pour mettre votre travail
=> git commit
	Rq : l'option "-m" permet de rentrer le message imm�diatement
	Ex : git commit -m "First commit"
=> git fetch
	Permet de mettre � jour votre repo miroir avec le repo online
=> git rebase [nom du serveur] [nom de la branche]
	Pour mettre � jour votre r�pertoire avec votre repo miroir
		Rq : le nom du serveur est souvent "origin"
	2 issues possibles !
	=> Vous n'avez pas de conflits, et bien parfait, passez directement  la commande suivante
	=> Vous avez des conflits, dans ce cas il va falloir les r�soudre m�thodiquement, faites ces actions jusqu'� ce qu'il 	n'y ait plus aucun conflits
		1) Regarder dans vos fichiers o� sont les conflits et r�solvez les
		2) git add [fichiers qui �taient en conflit]
		3) git rebase --continue
=> git push [nom du serveur] [nom de la branche]
	Pour finalement publier vos modifications sur le repo online pour que tout le monde puisse en profiter
		Rq : le nom du serveur est souvent "origin"

Le gros pav� c'est pour les purs et durs qui font du Git bash !
Pour ceux qui utilisent des IDE, voici la marche � suivre, mais ce n'est pas pour autant qu'il ne faut pas lire ce que j'ai �crit parce que c'est bien de savoir un peu comment �a marche !
Pour les utilisateurs de GitKraken : 
=> Dans la barre du dessus, � c�t� de Redo, il y a pull, cliquez sur la petite fl�che � gauche et selectionnez Pull(rebase) comme action par d�faut.
Pourquoi faire cela ?
Parce que un pull classique c'est un fetch-merge, qui fait que si il y a des conflits, le merge s'en fout et vous avez tout sur les bras d'un coup alors que un fetch-rebase, en cas de conflits, il les gere un par un sans briser l'unit� de la branche

Configuration :

git config --global +
			core.editor : pour choisir l"�diteur (nano conseill�)
			user.name "name" : pour d�finir le nom de l'utilisateur
			user.email email : pour d�finir l'email de l'utilisateur

BRANCHES

Pour cr�er une nouvelle branche et s'y placer :
git checkout -b [nom nouvelle branche] [nom du serveur]/[nom branche de r�f�rence]
Rq : ne pas oublier de push la nouvelle branche ! (pas besoin de commit, juste push) (git push [nom du serveur] [nom de la nouvelle branche])

git checkout [nom de la branche] : pour se positionner dans la branche qui accueillera les nouveaux commit, charge l'�tat de la branche, et uniquement cet �tat, tous les fichiers des autres branches vont disparaitre du repertoire
git merge branche_feature (pour appliquer les commit de la branche de nouvelles feature)
(git branch -d [nom de la branche � supprimer]) (pour supprimer la branche)

git branch -a : pour avoir le d�tail de toutes les branches

git branch -u origin/dev : pour mettre la branche de reference � dev

git push --set-upstream origin [nom de la branche] : pour changer de branche de push par default

AUTRES :

Si vous voulez plus de pr�cision sur les op�rations effectu�es � l'issu d'une commande, rajouter l'option "-v" apr�s la commande sous ce format : [commande (git fetch .. )] [option (-v ...)] [arguments (nom de la branche ...)]