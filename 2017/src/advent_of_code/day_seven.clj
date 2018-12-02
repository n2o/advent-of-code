(ns advent-of-code.day-seven
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn find-root [input]
  (let [relevant (filter #(string/includes? % "->")
                         (map string/trim (string/split-lines input)))
        maybe-root (set (map first
                         (map #(string/split % #" ") relevant)))
        carried (map set
                     (map #(string/split % #", ")
                          (map second
                               (map #(string/split % #" -> ") relevant))))
        diff (apply cset/difference (conj carried maybe-root))]
    (first diff)))


(def matcher (re-matcher #"(\d+)" "foo (42)"))



(def input-splitted
  (map string/trim (string/split-lines input)))
(def big-sample-splitted
  (map string/trim (string/split-lines big-sample)))
(def input-splitted big-sample-splitted)

(defn get-weight [line]
  (when-let [match (re-find #"([a-z]+)\ \((\d+)\).*" line)]
    {(second match) (last match)}))

(defn construct-weight-map [input-splitted]
  (apply merge (map #(get-weight %) input-splitted)))

(construct-weight-map input-splitted)


(defn get-discs [query]
  (let [found (first (filter #(.startsWith % query) input-splitted))]
    (when (string/includes? found "->")
      (-> found
       (string/split #" -> ")
       second
       (string/split #", ")))))

(reduce +
        (map #(read-string (get (construct-weight-map input-splitted) %))
             (get-discs "tknk")))

(def state (atom {}))

(defn foo [key current]
  (let [children (get-discs current)]
    (when-not (some nil? children)
      (doseq [child children]
        (swap! state update key conj child)
        (foo child child)))))

(reset! state {})

(let [children (get-discs "tknk")]
  (doseq [child children]
    (foo child child)))
(foo "cqmvs" "cqmvs")


(get-discs "cqmvs")
(map get-discs (get-discs "cqmvs"))
