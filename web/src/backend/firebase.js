// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";


const firebaseConfig = {
  apiKey: "AIzaSyCfYI7gjJKHvjTIlshueWpHpiWtQ5DuuqI",
  authDomain: "purrfect-recipes.firebaseapp.com",
  databaseURL: "https://purrfect-recipes-default-rtdb.firebaseio.com",
  projectId: "purrfect-recipes",
  storageBucket: "purrfect-recipes.appspot.com",
  messagingSenderId: "59454346156",
  appId: "1:59454346156:web:c3e1881e960f4f59ec6009",
  measurementId: "G-8QG43DQ2CL"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const database = getDatabase(app);

export { app,database }

