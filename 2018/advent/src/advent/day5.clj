(ns advent.day5
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def input
  (->> "day5"
       io/resource
       slurp))

(defn- generate-chars [start end]
  (map char (range (int start) (inc (int end)))))

(def pattern
  (re-pattern (string/join "|"
                           (apply conj
                                  (map str (generate-chars \a \z) (generate-chars \A \Z))
                                  (map str (generate-chars \A \Z) (generate-chars \a \z))))))

(defn first-part [s]
  (loop [current-string s]
    (let [reduced-string (string/replace current-string pattern "")]
      (if (or
           (= current-string reduced-string)
           (empty? reduced-string))
        (count reduced-string)
        (recur reduced-string)))))


;; -----------------------------------------------------------------------------

(def patterns
  (map re-pattern
       (map #(string/join "|" %)
            (map str (generate-chars \a \z) (generate-chars \A \Z)))))

(defn second-part [s]
  (first
   (sort
    (pmap first-part (map #(string/replace s % "") patterns)))))

(comment
  (second-part input)  ;; => 6968
  )
