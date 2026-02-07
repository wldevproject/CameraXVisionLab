# 📸 CameraX Vision Labs

> Experimental & educational project for exploring **CameraX-based vision features** on Android, implemented in **Classic View (XML)** and **Jetpack Compose** side by side.

---

## 🎯 Project Goals

- Build a **CameraX vision playground**
- Compare **Classic View vs Jetpack Compose**
- Keep **camera & vision logic reusable**
- Focus on **learning and experimentation**, not production

---

## 🧱 Project Structure

root
├── app                 # Launcher (choose Classic / Compose)
├── app-view            # Classic View-based implementation
├── app-compose         # Jetpack Compose implementation
└── core
    ├── camerax         # CameraX provider & lifecycle handling
    ├── analyzer        # Frame & vision base abstraction
    └── vision          # Vision algorithms (color, object, etc)

---

## 🧠 Design Principles

- Camera logic lives in **core**
- Vision algorithms live in **core/vision**
- UI only displays results
- Classic & Compose **share the same logic**
- One feature = one Activity
- No feature-per-module (flat & readable)
- Designed for clarity over abstraction

---

## ✅ Feature Progress Checklist

### 🧭 Navigation & Architecture
- [x] Multi-app launcher (Classic / Compose)
- [x] Android 11+ safe app navigation
- [x] Shared `CameraXProvider`
- [x] Clean separation: UI vs Camera vs Vision

---

### 📋 Menu
- [x] Classic Menu (XML + Activity)
- [x] Compose Menu (Composable)
- [x] Identical navigation flow

---

### 📸 Camera Preview
- [x] Classic Camera Preview
- [x] Compose Camera Preview (`AndroidView + PreviewView`)
- [x] Permission handling
- [x] Lifecycle-safe camera binding

---

### 🎨 Color Detection (STEP D)
- [x] Classic Color Detection
- [x] Compose Color Detection
- [x] HSV-based color probing
- [x] Custom `ColorProbeAnalyzer`
- [x] Real-time UI overlay (text only)
- [ ] Color calibration flow (planned)

---

### 📍 Position / Distance / Tracking
- [ ] Position Detection (left / center / right)
- [ ] Distance Estimation
- [ ] Object Tracking

---

### 🙂 Face / 📄 Document / 🔳 QR
- [ ] Face Detection
- [ ] Document Scanner
- [ ] QR / Barcode Scanner

---

### 🧪 Debug & Analyzer
- [ ] FPS overlay
- [ ] HSV value inspector
- [ ] Frame latency analyzer

---

## 🔬 Current Focus

Color detection is intentionally **simple**:

- No calibration yet
- No object shape detection
- No bounding boxes
- Just **“what color is currently visible?”**

Calibration and persistence will be handled in a **separate feature flow**, not mixed with probing logic.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **Camera**: CameraX
- **UI**: XML (Classic) & Jetpack Compose
- **Architecture**: Activity-based
- **Min SDK**: 28+

---

## ⚠️ Disclaimer

This project is:
- ❌ Not production-ready
- ❌ Not optimized for performance
- ❌ Not a full computer vision framework

This project is intended for:
- Learning CameraX
- Understanding vision pipelines
- Comparing UI approaches
- Rapid experimentation

---

## 🚀 Next Planned Feature

### Object Detection
- Start with single object
- No ML framework initially
- Focus on:
    - object presence
    - bounding region
    - relative position
