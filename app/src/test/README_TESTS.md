# Tests Unitaires et Fonctionnels - Proxymed

Ce document décrit les tests implémentés pour l'application Proxymed.

## Structure des Tests

### Tests Unitaires (`app/src/test/`)

Les tests unitaires s'exécutent sur la JVM locale (sans émulateur Android) et sont plus rapides.

#### Modèles (`data/model/`)
- **UtilisateurTest.java** : Tests pour la classe `Utilisateur`
  - Test du constructeur avec infirmier et médecin
  - Test des getters et setters
  - Test de la gestion des valeurs null

- **PatientEntityTest.java** : Tests pour la classe `PatientEntity`
  - Test du constructeur
  - Test des getters et setters
  - Test de la méthode `toString()`
  - Test avec et sans médecin assigné

- **VisiteEntityTest.java** : Tests pour la classe `VisiteEntity`
  - Test du constructeur avec tous les paramètres
  - Test des getters et setters
  - Test avec et sans médecin
  - Test des différents statuts

#### Repositories (`data/repository/`)
- **UtilisateurRepositoryTest.java** : Tests pour `UtilisateurRepository`
  - Tests de base pour les opérations CRUD
  - Note: Les tests complets nécessitent une base de données de test Room

- **PatientRepositoryTest.java** : Tests pour `PatientRepository`
  - Tests d'initialisation
  - Tests des opérations CRUD

- **VisiteRepositoryTest.java** : Tests pour `VisiteRepository`
  - Tests de toutes les méthodes de recherche
  - Tests des opérations CRUD

#### ViewModels (`viewmodel/`)
- **UtilisateurViewModelTest.java** : Tests pour `UtilisateurViewModel`
  - Tests d'initialisation
  - Tests des méthodes de mise à jour
  - Tests des méthodes LiveData

- **PatientViewModelTest.java** : Tests pour `PatientViewModel`
  - Tests d'initialisation
  - Tests de récupération des patients

- **VisiteViewModelTest.java** : Tests pour `VisiteViewModel`
  - Tests de toutes les méthodes du ViewModel

### Tests Fonctionnels (`app/src/androidTest/`)

Les tests fonctionnels (instrumentés) s'exécutent sur un appareil Android ou un émulateur.

#### Interface Utilisateur (`ui/auth/`)
- **LoginFragmentTest.java** : Tests pour `LoginFragment`
  - Test de création du fragment
  - Test de connexion avec identifiants valides
  - Test des SharedPreferences après connexion

#### Base de Données (`data/database/`)
- **AppDatabaseTest.java** : Tests complets pour la base de données Room
  - Test d'insertion et récupération d'utilisateurs
  - Test de connexion par email et mot de passe
  - Test de filtrage par type (infirmier/médecin)
  - Test d'insertion et récupération de patients
  - Test de mise à jour de patients
  - Test d'insertion et récupération de visites
  - Test de suppression

#### Repositories (`data/repository/`)
- **RepositoryIntegrationTest.java** : Tests d'intégration
  - Test du workflow complet (médecin → infirmier → patient → visite)
  - Tests d'intégration pour chaque repository

## Dépendances de Test

Les dépendances suivantes ont été ajoutées au fichier `build.gradle.kts` :

```kotlin
// Mockito pour les tests unitaires
testImplementation("org.mockito:mockito-core:5.1.1")
testImplementation("org.mockito:mockito-inline:5.1.1")

// Robolectric pour tester les composants Android sans émulateur
testImplementation("org.robolectric:robolectric:4.11.1")

// Room testing
testImplementation("androidx.room:room-testing:2.6.1")
androidTestImplementation("androidx.room:room-testing:2.6.1")

// Architecture Components testing
testImplementation("androidx.arch.core:core-testing:2.2.0")
androidTestImplementation("androidx.arch.core:core-testing:2.2.0")

// Espresso contrib
androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

// Fragment testing
debugImplementation("androidx.fragment:fragment-testing:1.6.2")
```

## Exécution des Tests

### Tests Unitaires
```bash
./gradlew test
```

### Tests Fonctionnels (Instrumentés)
```bash
./gradlew connectedAndroidTest
```

### Tous les Tests
```bash
./gradlew check
```

## Notes Importantes

1. **Base de Données de Test** : Les tests utilisent une base de données en mémoire (`Room.inMemoryDatabaseBuilder`) pour éviter de modifier la base de données de production.

2. **Robolectric** : Les tests unitaires utilisent Robolectric pour simuler l'environnement Android sans avoir besoin d'un émulateur.

3. **LiveData** : Pour tester les LiveData dans les tests unitaires, il faudrait utiliser `InstantTaskExecutorRule` ou observer les valeurs de manière synchrone.

4. **Threads** : Certains repositories utilisent des threads pour les opérations asynchrones. Dans les tests, on utilise `allowMainThreadQueries()` pour simplifier.

## Améliorations Futures

- Ajouter des tests Espresso pour l'interface utilisateur complète
- Ajouter des tests de performance
- Ajouter des tests de sécurité (validation des mots de passe, etc.)
- Améliorer la couverture de code avec des tests plus complets pour les repositories

