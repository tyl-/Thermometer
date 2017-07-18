# Thermometer [![Build Status](https://travis-ci.org/tyl-/Thermometer.svg?branch=master)](https://travis-ci.org/tyl-/Thermometer) [![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

This program simulates a thermometer. A GUI will allow the user to increase and decrease outside temperatures which will cause the inside temperature and the thermometer to slowly change to the outside temperature.

---

## Main Goals

The main goals for this program are:
- Completed: **GUI.**
- Completed: **User can increase/decrease temperature.**
- Completed: **User can switch temperature units.**
- Completed: **Thermometer slowly adapts to outside temperature.**

## Start Date

- March 16, 2017

## Initial Completion Date

- March 17, 2017

## Goal Changes

- Completed: **Thermometer image will change based on temperature.**

---

## Updates

- July 18, 2017
Refactored code and added comments.
Added status text on the middle panel.
Changed the panel layout and thermometer image.
Added visual indicators for outside temperature changes on the thermometer.
Added celcius markers for the thermometer.

---

## Possible Improvements

- Refactor.
- Optimizations.
- Unit Tests.
- Documentations.

---

## Notes

- Thermometer image updates every ~1 second.
- Thermometer adapts to outside temperature based on temperature difference.
- Difference change are <5 = 0.1C; >5 = .15C; >10C = .25C; >20C = 0.5C
- Screenshot:

![Screenshot](/screenshots/03-17-17.jpg?raw=true "Screenshot 07-18-17")

---

## Special Thanks To

- <a href="http://fvcproductions.com" target="_blank">FVCproductions</a> for their README Template.
- <a href="https://travis-ci.org/" target="_blank">Travis CI</a>.
---

## License

[![License](http://img.shields.io/:license-mit-blue.svg?style=flat-square)](http://badges.mit-license.org)

- **[MIT license](http://opensource.org/licenses/mit-license.php)**