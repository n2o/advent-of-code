(ns advent-of-code.day-nineteen
  (:require [clojure.string :as string]))

(def puzzle "
                
     |          
     |  +--+    
     A  |  C    
 F---|----E|--+ 
     |  |  |  D 
     +B-+  +--+ 
                ")

(def lines (rest (string/split-lines puzzle)))

(def array2d (to-array-2d lines))

(def directions
  {:down #{\| \+}})

(defn startpoint []
  (string/index-of (second lines) "|"))

(defn step [y x direction array2d]
  (case direction
    :down  [(inc y) x (aget array2d (inc y) x)]
    :up    [(dec y) x (aget array2d (dec y) x)]
    :left  [y (dec x) (aget array2d y (dec x))]
    :right [y (inc x) (aget array2d y (inc x))]))

(step 1 5 :down array2d)

