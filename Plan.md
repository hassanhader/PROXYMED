# 🔥 Plan de match (Étapes claires pour le projet Proxymed)

---

## 📅 Jour 1 - Mise en place du projet & base commune

### ✅ 1. Création du projet Android Studio
- Projet **Java** avec un template vide.
- **Nom**: ProxymedApp
- **Min SDK**: 26 (ou selon consignes du prof)
- Choisir **ConstraintLayout** pour tous les écrans.

### ✅ 2. Initialisation Git & GitLab
- Créer un dépôt **GitLab** pour votre équipe.
- Cloner le projet local et le pousser.
- Configurer le partage avec tes coéquipiers.
- Créer des **branches** pour chaque fonctionnalité (ex: `feature/authentication`, `feature/patient-management`).

### ✅ 3. Structure de base MVC + Room
- **Package : `ui`** pour les activités et fragments.
- **Package : `data`** pour les modèles (Patient, Visit, etc.).
- **Package : `repository`** pour gérer les accès aux données.
- **Package : `viewmodel`** pour la logique de présentation.

---

## 📅 Jour 2 - Authentification (infirmiers et médecins)

### ✅ 4. Création des écrans d’authentification (Login, SignUp, ResetPassword)
- 3 **Fragments** : Connexion / Inscription / Reset Password.
- Validation de base (champs non vides, emails valides).

### ✅ 5. Base de données Room pour les utilisateurs (infirmiers/médecins)
- Créer les entités :
    - `NurseEntity` (nom, prénom, email, téléphone, etc.)
    - `DoctorEntity` (nom, prénom, email, spécialité, numéro de licence)
- DAO pour insérer et vérifier les utilisateurs.
- Repository et ViewModel pour gérer les appels.
- Navigation entre les fragments.

### ✅ 6. Sécurité basique
- Hash du mot de passe (même si c’est local).
- Bloquer plusieurs tentatives avec un simple compteur.

---

## 📅 Jour 3 - Dashboard infirmier / médecin

### ✅ 7. Créer les écrans de base (main screens)
- Activité principale **NurseActivity** avec `BottomNavigationView` contenant :
    - Visites (`VisitListFragment`) : Liste des rapports/visites.
    - Patients (`PatientListFragment`) : Liste des patients.
    - Profil (`ProfileFragment`) : Profil de l’infirmier.

- Même structure pour **médecin**, adapté :
    - Rapports à valider
    - Patients
    - Profil

### ✅ 8. RecyclerView pour les listes de patients et visites.

### ✅ 9. Base de données Room pour patients et visites.
- Créer les entités :
    - `PatientEntity` (nom, prénom, date naissance, adresse, etc.)
    - `VisitEntity` (patient_id, date, constantes vitales, notes, etc.)
- Créer les relations (un patient a plusieurs visites).

---

## 📅 Jour 4 - Gestion des patients et visites

### ✅ 10. Écrans CRUD patients
- Ajouter/modifier/supprimer un patient.
- Sélectionner un patient pour voir son profil.

### ✅ 11. Écrans CRUD visites
- Ajouter/modifier/supprimer une visite.
- Lors de l’ajout de visite, sélectionner un patient existant.
- Enregistrer les constantes vitales.
- Prendre des notes.

### ✅ 12. Prise de photos (via caméra)
- Ajouter un bouton **"Prendre une photo"** sur le formulaire de visite.
- Utiliser `ACTION_IMAGE_CAPTURE`.
- Enregistrer la photo dans la mémoire interne et l’afficher dans le formulaire.

### ✅ 13. Géolocalisation de la visite
- Ajouter un bouton **"Géolocaliser"**.
- Utiliser `FusedLocationProviderClient`.
- Enregistrer les coordonnées latitude/longitude avec la visite.

---

## 📅 Jour 5 - Partie médecin

### ✅ 14. Écran de connexion médecin.

### ✅ 15. Dashboard médecin
- Liste des patients.
- Liste des rapports à valider.
- Consultation d’un rapport.
- Ajouter une annotation médicale.

### ✅ 16. Recherche patients / rapports
- Barre de recherche sur les listes.
- Filtrer en live sur le `RecyclerView`.

---

## 📅 Jour 6 - Finalisation et tests

### ✅ 17. Tests et débogage
- Passer à travers tous les cas d’utilisation.
- Vérifier les transitions entre les écrans.
- Tester la création, modification et suppression des patients et visites.
- Vérifier la persistance des données.

### ✅ 18. Préparation de la remise
- Compléter le document de spécifications (**30%**).
- Mettre à jour le **README.md** de GitLab (instructions pour exécuter l’appli).
- Vérifier que le prof et le correcteur ont les accès GitLab.

---

## 📅 Jour 7 - Backup + Design (optionnel)

### ✅ 19. Design Figma (optionnel)
- Utiliser **Figma** pour faire les maquettes, mais les convertir manuellement en XML.
- Il existe des plugins (ex: **Figma to Android Studio**), mais rarement 100% propre.
- **Conseil** : valider le design avec Figma, mais coder directement en `ConstraintLayout`.

### ✅ 20. Dernière validation et dépôt final

---

## 🔧 Structure de projet recommandée

```text
com.example.proxymed
│-- data
│   ├── model
│   │   ├── Nurse.java
│   │   ├── Doctor.java
│   │   ├── Patient.java
│   │   └── Visit.java
│   ├── database
│   │   ├── AppDatabase.java
│   │   ├── NurseDao.java
│   │   ├── DoctorDao.java
│   │   ├── PatientDao.java
│   │   └── VisitDao.java
│   └── repository
│       ├── NurseRepository.java
│       ├── DoctorRepository.java
│       ├── PatientRepository.java
│       └── VisitRepository.java
│
│-- ui
│   ├── auth
│   │   ├── LoginFragment.java
│   │   ├── SignUpFragment.java
│   │   └── ResetPasswordFragment.java
│   ├── nurse
│   │   ├── NurseActivity.java
│   │   ├── VisitListFragment.java
│   │   ├── PatientListFragment.java
│   │   └── ProfileFragment.java
│   ├── doctor
│   │   ├── DoctorActivity.java
│   │   ├── ReportListFragment.java
│   │   ├── PatientListFragment.java
│   │   └── ProfileFragment.java
│   └── visit
│       ├── VisitDetailActivity.java
│       └── AddVisitFragment.java
│
│-- viewmodel
│   ├── NurseViewModel.java
│   ├── DoctorViewModel.java
│   ├── PatientViewModel.java
│   └── VisitViewModel.java
│
│-- util
│   ├── GeolocationHelper.java
│   └── CameraHelper.java
│
│-- MainActivity.java
│-- App.java
