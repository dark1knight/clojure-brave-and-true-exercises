(ns fwpd.core
  (:gen-class))

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int [str] (Integer. str))

(def conversions {:name identity :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a csv into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn glitter-filter
  [min-glitter records]
  (filter #(>= (:glitter-index %) min-glitter) records))

(defn mapify
  "return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  ; map unmapped rows into mapped maps.
  (map (fn [unmapped-row]
         ; take the vector and reduce it into a map
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                   {}
                   ; map the vampire keys and row into a vector
                   (map vector vamp-keys unmapped-row)))
       rows)
  )

(defn name-the-vampires
  [min-glitter]
  (map
   :name
   (glitter-filter min-glitter (mapify
                                (parse (slurp filename))))))

(defn validate
  [attr-map]
  (and
   (contains? attr-map :name)
   (contains? attr-map :glitter-index)))

(defn validate
  [vamp-keys record]
  ; if any of the keys are not found, record is not valid.
  (if (some
       #(not %)
       ; generate a sequence of bools that indicates whether a key exists in this map
       (map
        #(boolean (% record))
        vamp-keys))
    false
    true
  ))

(def suspects (mapify (parse (slurp filename))))

(defn stringify
  [records]
  (clojure.string/join "\n"
  (map #(clojure.string/join ", " %) (map vals records)))
  )

(defn append
  [new-suspect]
  (let [current-suspects (mapify (parse (slurp filename)))]
    (if (validate new-suspect)
      (conj current-suspects new-suspect)
      current-suspects
      )
    ))




(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
