(ns advent.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as cset]))

(def input
  (->> "day2"
       io/resource
       slurp
       string/split-lines))

(defn- string->match-vector [s]
  (let [freqs (cset/map-invert (frequencies s))]
    (cond
      (and (contains? freqs 3) (contains? freqs 2)) [1 1]
      (contains? freqs 3) [0 1]
      (contains? freqs 2) [1 0]
      :else [0 0])))

(defn first-part [col]
  (let [calc-vectors (map string->match-vector col)
        [twos threes] (reduce #(mapv + %1 %2) calc-vectors)]
    (* twos threes)))


;; -----------------------------------------------------------------------------

(defn- pairs [col]
  (for [x col y col
        :when (not= x y)]
    [x y]))

(defn- find-matching-strings [pairs]
  (for [[e1 e2] pairs
        :let [matching-chars (map = e1 e2)]
        :when (= 1 ((frequencies matching-chars) false))]
    [e1 (.indexOf matching-chars false)]))

(defn second-part [col]
  (let [[s idx] (first (find-matching-strings (pairs col)))]
    (str (subs s 0 idx) (subs s (inc idx) (count s)))))
