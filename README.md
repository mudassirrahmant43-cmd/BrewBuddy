# BrewBuddy — Specialty Coffee Ordering App

> *"Your coffee. Your moment."*

**BrewBuddy** is a modern, native Android application designed for coffee lovers to discover artisanal drinks, customize every detail of their order, manage a real-time shopping cart, and submit orders smoothly.

---

## ☕ Key Features

### 1. **Home Screen**
- **Branded Welcome Header**: Warm personalized greeting and BrewBuddy logo.
- **Featured Brew Card**: Highlighted seasonal beverage (*Artisanal Cold Brew*) with instant customization and ordering.
- **Popular Coffee Grid**: Quick view of top-rated specialty drinks.
- **Direct Navigation**: Seamless shortcut to explore the complete coffee menu.

### 2. **Interactive Coffee Menu**
- **8 Artisanal Coffee Offerings**: Classic Espresso, Caffè Americano, Velvet Cappuccino, Caffè Latte, Dark Chocolate Mocha, Caramel Cloud Latte, Iced Coffee, and Artisanal Cold Brew.
- **Live Category Filters**: Filter drinks in real-time by *All Coffee*, *Hot Coffee*, *Iced Coffee*, and *Specialty*.

### 3. **Drink Customization**
- **Temperature Control**: Choose between Hot and Iced preparation.
- **Size Options**: Small, Medium (+$0.50), and Large (+$1.00).
- **Add-ons & Extras**: Extra Espresso Shot (+$1.00), Vanilla Syrup (+$0.50), Caramel Drizzle (+$0.50), Whipped Cream (+$0.75), Chocolate Topping (+$0.50).
- **Quantity Adjuster**: Flexible quantity controls (1-10) with live price calculation before adding to cart.

### 4. **Real-time Shopping Cart**
- **Distinct Configurations**: Prevents incorrect merging by grouping unique drink customizations separately.
- **Item Controls**: Adjust quantities directly or remove items.
- **Price Breakdown**: Displays subtotal, estimated tax (8%), and grand total.
- **Empty State**: Friendly illustration and call-to-action when cart is empty.
- **Cart Badge**: Live item count badge on bottom navigation tab.

### 5. **Checkout & Order Confirmation**
- **Customer Details**: Validates customer name input.
- **Pickup Method**: In-Store Collection vs. Curbside Pickup options.
- **Order Confirmation**: Generates a unique reference code (`#BB-XXXX`) with pickup timing estimate and clears cart upon confirmation.

### 6. **Profile & Settings**
- **Store Info**: Highlights favorite café location and operating details.
- **App Preferences**: Order notifications, default payment preference, and theme settings.
- **About & Attribution**: Credits original project history and licensing.

---

## 🎨 Visual Identity & Palette

Designed with an independent specialty coffee shop aesthetic:
- **Espresso Brown** (`#3E2723`): Primary brand color and app bar accent.
- **Warm Caramel** (`#C67C38`): Primary actions, highlights, and badge accents.
- **Muted Sage** (`#6B8E70`): Secondary accents.
- **Warm Cream** (`#FAF7F2`): Screen background for high contrast and readability.

---

## 🛠 Tech Stack & Requirements

- **Language**: 100% Native Java
- **UI Architecture**: Android XML Layouts, Material Components (M3/M2), BottomNavigationView, BottomSheetDialogFragment, RecyclerView, CardView, ChipGroup.
- **Minimum SDK**: API 21 (Android 5.0)
- **Compile SDK / Target SDK**: API 31 (Android 12)
- **Gradle**: 7.3.3 / Android Gradle Plugin 7.2.2
- **Java Version**: JDK 1.8

---

## 🚀 Building and Running

### Build Debug APK
- **On Windows**:
  ```cmd
  gradlew.bat assembleDebug
  ```
- **On macOS / Linux**:
  ```bash
  ./gradlew assembleDebug
  ```

### Run Unit Tests
```cmd
gradlew.bat testDebugUnitTest
```

The generated debug APK will be located at:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 📜 Attribution & License

This application was transformed from the original open-source **Just Java** sample app created for the Udacity Android for Beginners course.

Copyright 2015 The Android Open Source Project, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
