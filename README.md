# 2018-POC-5
## Backend
### Installation
1. Copier le fichiers [`backend/src/main/resources/application.properties.default`](backend/src/main/resources/application.properties.default) dans le même dossier et le renommer `application.properties`. Ce fichier sert de configuration pour le server de backend. 4 lignes nous intéressent particulièrement :
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/groupir?serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=
    spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
    ``` 
    Ces lignes permettent de configurer la connexion à la base de données utilisée pour l'application.
    
    Dans la première vous pouvez modifier l'adresse du serveur de la base de données (par défaut `localhost:3306`) ainsi que le nom de la base de données (par défaut `groupir`).
    
    Les 2 lignes suivantes servent à configurer l'utilisateur utilisé par le backend pour se connecter à la base de données. (par défaut l'utilisateur `root` sans mot de passe)
    
    La dernière ligne permet de configurer le type de serveur de base de données utilisé pour que le backend puisse créer correctement ses requètes
    
    Le reste de la configuration sert à paramètrer les tokens d'authentification et la manière dont le backend doit gérer le schémas de la base de données
    
2. Depuis 
`backend/` exécuter la commande :
    - `./gradlew bootRun` pour un OS Unix ou depuis Windows PowerShell
    - `gradlew bootRun` depuis le cmd Windows
    
    Gradle va alors démarrer, télécharger les dépendences, compiler le codes, puis démarrer le serveur.
    
    Une fois qu'il aura démarré c'est à dire que le message :
     
     `yyyy-mm-dd hh-mm-ss.xxx  INFO 5256 --- [  restartedMain] com.groupir.backend.BackendApplication   : Started BackendApplication in X seconds (JVM running for X)`
      
      apparaît dans la console, le backend aura initialisé le schéma dans la base de données. 
      
      Le backend est alors complètement fonctionnel, mais sans données.
      
3. (optionnel) Un fichier contenant des données de tests est disponible dans le dépot. Il s'agît de [`backend/src/test/resources/sql/groupir_address.sql`](backend/src/test/resources/sql/groupir_address.sql). Le schémas n'est pas présent dans le script, seulement les données sous forme d'`INSERT`