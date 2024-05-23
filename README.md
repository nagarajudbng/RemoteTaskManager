<H3>Remote Task Manager</H3>
<b>App Overview</b>

Remote Task Manager is an Android application developed using Kotlin. It follows the MVVM (Model-View-ViewModel) Clean Architecture pattern and employs Google Play Feature Delivery and Multi-Module Architecture to deliver a modular, scalable, and optimized user experience. The app leverages Jetpack Compose for UI development, Dagger Hilt for dependency injection, and integrates Room database and Firebase for data storage, including image storage with Firebase Storage. Firebase Authentication is used for user authentication, and Coil is employed for efficient image loading. Navigation within the app is handled using Jetpack Compose Navigation. Continuous Integration (CI) is achieved through GitHub Actions, with unit test cases ensuring code quality and reliability.

<p float="left">
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/2824fbd7-10b4-4723-8ed2-17ec18c17ff7" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/422ae98c-2293-4ebd-a767-afc12d87d904" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/000917c9-6fff-4e7a-9ec5-80338e4f6346" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/e5698eef-7fd5-4bb2-ac67-0d38c6c0fb74" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/8d504d94-78fc-4f04-b635-beacb041c29d" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/a47197d4-b7d1-45dc-977d-618a050a517c" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/1097eb1b-1c10-4505-bbf2-4236661ec1dd" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/b6ab9062-2d66-498d-823d-a2a78417ac5d" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/c95f174a-a5b3-49ce-853d-e516a1afceb4" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/18472d53-d566-408f-bb0b-211c7a8eab10" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/0576da63-27cc-42f7-a683-9c32978f8462" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/8be7af09-d72a-4e90-9c55-6811edbcb6e5" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/dc013afd-bdc8-4761-a499-345b66610a2c" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/6b573857-e582-4873-805b-ee22c6e9780b" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/f32cd576-c9f9-4b7c-b5d8-73eed93a40f8" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/0bff9f0f-9995-4cd4-aa34-539fde400836" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/25013fc7-abbf-4283-885c-68a1a6b11bc8" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/c6975725-95d4-48f7-b67f-40371b9c316a" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/164a8693-03ca-4447-89ed-8f1b602d43db" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/6bce7c83-209a-4559-9c4b-a93ce9e25c17" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/9b24ce7f-21b5-4d52-be82-bd02cdb74539" width="100" />
<img src="https://github.com/nagarajudbng/RemoteTaskManager/assets/2852460/d36a1574-f407-42c8-b7ff-e963af48e237" width="100" />
</p>

Features:
**1. Task Synchronization:** Tasks are synchronized with Firebase Database, enabling users to access and manage tasks across devices seamlessly. This feature ensures data consistency and availability across platforms.
**2. Alarm and Reminder:** Users can set alarms and reminders for their tasks, enhancing productivity and ensuring timely completion of tasks.
**3. Search Task:** Users can search for specific tasks using keywords or filters, facilitating quick and efficient task retrieval.
4. Filter Tasks: Tasks can be filtered based on various criteria such as priority, due date, or category, aiding users in organizing and prioritizing their tasks effectively.
5. Task Update in List: Users can update task details directly from the task list, providing a streamlined and intuitive user experience without navigating to separate screens.
6. Offline Functionality: The app works offline, allowing users to access and manage tasks even without an internet connection. Changes made offline are seamlessly synced with Firebase Database when the device reconnects.
7. User Profile: This includes details such as the user's name, email address, username, profile picture, and possibly a short bio or description, User Profile with Image URL, After the image is successfully uploaded to Firebase Storage, store the download URL of the image in Firebase Firestore or Realtime Database along with the user's other profile information. This URL can be used to retrieve and display the user's profile image in your app.
Screens:
1. Sign-in Screen: Allows users to sign in to their accounts using Firebase Authentication.
2. Sign-up Screen: Enables new users to create accounts and register for the app.
3. Profile Screen: Displays user profile information and allows users to manage account settings.
4. Home Screen: Shows a list of tasks, with options for searching, filtering, and updating tasks directly from the list.
5. Create Task Screen: Allows users to create new tasks, including setting reminders and alarms.
Architecture and Components:
* MVVM Clean Architecture: The app follows the MVVM pattern, separating concerns into Model, View, and ViewModel layers. This architecture promotes maintainability, testability, and scalability.
* Model: Represents the data and business logic of your application. This includes data classes for tasks, user authentication, and any other entities in your app. In your case, the models handle task data stored locally in Room database and remotely in Firebase Realtime Database or Firestore.
* View: Represents the UI of your application. In Jetpack Compose, your UI components (Composables) are part of the View layer. They observe changes in the ViewModel and update the UI accordingly. Views are responsible for displaying tasks, user authentication screens, and other UI elements.
* ViewModel: Acts as a mediator between the Model and the View. ViewModels contain the business logic for interacting with the data layer and preparing data to be displayed in the UI. They also handle UI-related logic such as navigation. ViewModel instances survive configuration changes (like screen rotations) and are retained as long as the associated UI component is active. You might have ViewModels for tasks, user authentication, and possibly other features like task filtering and searching.
* Repository: The Repository pattern abstracts the data sources from the ViewModel. It provides a clean API for the ViewModel to interact with both local (Room database) and remote (Firebase) data sources. This separation of concerns allows for easier testing and swapping out data sources if needed.
* Use Cases/Interactors (Optional): In some MVVM implementations, Use Cases or Interactors sit between the ViewModel and Repository layers. They encapsulate complex business logic or orchestrate multiple repository calls. For example, you might have a SyncTasksUseCase responsible for synchronizing tasks between the local and remote databases.
* Google Play Feature Delivery: Integrating Google Play Feature Delivery into the app enhances the app distribution process, especially for large apps or apps with multiple features. This allows the app to request the download of specific modules when needed and handle the installation process seamlessly. Google Play Feature Delivery allows optimizing app size and user experience by delivering only the necessary features and resources when needed. Can use app bundles to provide tailored experiences for different device configurations and reduce the initial download size of the app.
* Firebase Realtime Database: Firebase Realtime Database is a cloud-hosted NoSQL database that stores data as JSON and synchronizes it in real-time to every connected client. It's a flexible, scalable database solution suitable for real-time applications like chat apps, collaborative tools, and live updates.
* Firebase Firestore: Firestore is a flexible, scalable NoSQL cloud database that stores data in documents and collections. It offers more advanced querying and scalability features compared to the Realtime Database and is designed for scalability and performance.
* Multi-Module Architecture: Modularizes the app into separate feature modules, facilitating code organization, reusability, and independent testing.
* Jetpack Compose: Used for building modern, declarative UIs, providing a seamless and intuitive user experience.
* DI Dagger Hilt: Manages dependency injection, ensuring modularization, testability, and scalability.
* Room Database: Integrates Room database for local data storage and Firebase for remote data storage, including image storage with Firebase Storage.
* StateFlow: MVVM architecture typically uses  StateFlow in Jetpack Compose to represent observable data streams. These data streams are observed by the UI components (Views) and updated whenever the underlying data changes. StateFlow ensures that the UI remains synchronized with the data in the ViewModel.
* Authentication: Implements Firebase Authentication for secure user authentication.
* Image Loading: Utilizes Coil for efficient loading and caching of images, enhancing performance and user experience.
* JetPack Navigation: Handles navigation between screens using Jetpack Compose Navigation, ensuring a consistent and coherent user journey.
* Test: Includes unit test cases using Mockito and JUnit frameworks, ensuring code quality, reliability, and maintainability.
* CI: Implements continuous integration using GitHub Actions, automating build, test, and deployment processes for efficient development and delivery.
Overall, "Remote Task Manager" is a comprehensive and robust task management app that offers a seamless user experience, leveraging modern Android development practices and technologies to deliver a reliable and feature-rich solution for task organization and productivity.


