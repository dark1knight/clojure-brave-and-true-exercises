(ns practice.core
  (:gen-class))

;; a function to lazily generate triangular numbers
(defn gen-tri
  "Generates lazy sequence of triangular numbers"
  ([] (gen-tri 0 1)) ;; the default case
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (gen-tri new-sum (inc n)))))))

;; test whether a number is triangular by taking numbers from a sequence until one that is
;; greater than or equal to.
(defn triangular?
  "Figure out of the number is triangular - ie. 1, 3, 6, 10, 15, ..."
  [n]
  (let [tri (gen-tri)]
    (= n (last (take-while #(>= n %) tri))))
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
