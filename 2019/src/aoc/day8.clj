(ns aoc.day8
  (:require [clojure.java.io :as io]))

(def input
  (map #(keyword (str %))
       (-> "day8"
           io/resource
           slurp)))

(def pixels (* 25 6))

(def solution1
  "1620"
  (let [entry (apply min-key :0
                     (map frequencies (partition pixels input)))]
    (* (:1 entry) (:2 entry))))
