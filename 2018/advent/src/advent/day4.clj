(ns advent.day4
  (:require [clj-time.format :as timef]
            [clj-time.core :as time]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def sample
  ["[1518-11-01 00:00] Guard #10 begins shift"
   "[1518-11-01 00:05] falls asleep"
   "[1518-11-01 00:25] wakes up"
   "[1518-11-01 00:30] falls asleep"
   "[1518-11-01 00:55] wakes up"
   "[1518-11-01 23:58] Guard #99 begins shift"
   "[1518-11-02 00:40] falls asleep"
   "[1518-11-02 00:50] wakes up"
   "[1518-11-03 00:05] Guard #10 begins shift"
   "[1518-11-03 00:24] falls asleep"
   "[1518-11-03 00:29] wakes up"
   "[1518-11-04 00:02] Guard #99 begins shift"
   "[1518-11-04 00:36] falls asleep"
   "[1518-11-04 00:46] wakes up"
   "[1518-11-05 00:03] Guard #99 begins shift"
   "[1518-11-05 00:45] falls asleep"
   "[1518-11-05 00:55] wakes up"])

(def sample-sleep
  "[1518-10-03 00:47] falls asleep")

(def sample-with-guard
  "[1518-07-26 23:50] Guard #487 begins shift")

(def sample-wake-up
  "[1518-11-21 00:55] wakes up")

(def guard-time-formatter (timef/formatter "yyyy-MM-dd HH:mm"))

(defn- parse-line [line]
  (let [timestamp (timef/parse guard-time-formatter (re-find #"\d+-\d+-\d+\ \d+:\d+" line))
        guard (re-find #"#\d+" line)
        guard-num (when guard (read-string (subs guard 1)))
        sleeps? (string? (re-find #"falls\ asleep" line))
        wakes-up? (string? (re-find #"wakes\ up" line))
        state (cond sleeps? :sleep (or wakes-up? (number? guard-num)) :awake)]
    (merge
     {:time timestamp
      :state state}
     (when guard-num {:guard guard-num}))))

(def parsed-lines
  (->> "day4"
       io/resource
       slurp
       string/split-lines
       (map parse-line)
       (sort-by :time)))

(sort-by :time parsed-lines)

(parse-line sample-sleep)
(parse-line sample-with-guard)
(parse-line sample-wake-up)

(def parsed-sample
  (map parse-line sample))


(def test-guards
  (partition-by :guard parsed-sample))

(defn build-guards-and-actions [input]
  (loop [guards test-guards
         acc []]
    (let [data (take 2 guards)
          guard (ffirst data)
          actions (second data)]
      (if (empty? guards)
        acc
        (recur (drop 2 guards)
               (conj acc {:guard (:guard guard) :actions actions}))))))

(def myguard (first (build-guards-and-actions test-guards)))


(defn slept [[t1 t2]]
  ;; (let [times (map :time (take 2 (:actions myguard)))])
  (time/in-minutes (time/interval t1 t2)))

(comment
  (slept (map :time (take 2 (:actions myguard))))
  )
