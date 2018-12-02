(ns advent-of-code.day-four
  (:require [clojure.string :as string]
            [clojure.math.combinatorics :as comb]))

(defn passphrase? [pp]
  (apply distinct? (string/split pp #" ")))

(defn no-permutation? [pp]
  (empty? (filter (fn [[s1 s2]] (= s1 s2))
                  (comb/combinations
                   (map #(sort (vec %)) (string/split pp #" "))
                   2))))

(defn count-true [f ppcol]
  (count (filter true? (map f (string/split ppcol #"\n")))))
