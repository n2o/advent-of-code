(ns advent-of-code.day-two
  (:require [clojure.math.combinatorics :as comb]))

(defn- diff [row]
  (- (apply max row) (apply min row)))

(defn part-one [input]
  (reduce + (map diff input)))

(defn part-two [input]
  (reduce +
          (flatten
           (map
            #(map (fn [[n1 n2]]
                    (cond
                      (= 0 (mod n1 n2)) (/ n1 n2)
                      (= 0 (mod n2 n1)) (/ n2 n1)
                      :else 0))
                  (comb/combinations % 2))
            input))))
