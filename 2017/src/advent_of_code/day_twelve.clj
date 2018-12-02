(ns advent-of-code.day-twelve
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn trans-closure
  "Calculates the transitive clojure of a relation given as a set of vectors,
  e.g. (trans-closure #{[1 2] [2 3]}) yields #{[1 2] [2 3] [1 3]}. Taken from
  https://gist.github.com/bendisposto/1471058"
  [e]
  (letfn [(f [x] (for [[a b] x [c d] x :when (= b c)] [a d]))]
    (let [e2 (set (f e))]
      (if (cset/subset? e2 e)
        e
        (recur (cset/union e e2))))))

(defn combinations [splitted]
  (set
   (apply concat
          (map
           #(for [x (first %)
                  y (string/split (second %) #", ")]
              [(read-string (str x)) (read-string y)])
           splitted))))

(defn count-zeros [input]
  (let [lines (string/split-lines input)
        splitted (map #(string/split % #" <-> ") lines)
        combs (combinations splitted)]
    [(trans-closure combs)
     (count (filter #(= 0 (second %))
                    (trans-closure combs)))]))

;; (count-zeros input)
