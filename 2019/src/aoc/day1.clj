(ns aoc.day1
  (:require [aoc.lib :as lib]))

(def input (lib/read-multiline-file "day1"))

(defn calc-fuel [mass]
  (- (int (/ mass 3)) 2))

(defn fuel-for-fuel [mass]
  (apply + (take-while pos? (iterate calc-fuel (calc-fuel mass)))))

(def solution1
  "3448043"
  (apply + (map calc-fuel input)))

(def solution2
  "5169198"
  (apply + (map fuel-for-fuel input)))
