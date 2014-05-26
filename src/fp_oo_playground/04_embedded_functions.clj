(ns fp-oo-playground.04-embedded-functions)

(def make
  (fn [type & args]
    (apply type args)))

(def send-to
  (fn [object message & args]
    (apply (message (:__methods__ object) object args))))

(def Point
  (fn [x y]
    {:x x
     :y y
     :__class_symbol__ 'Point
     :__methods__ {
                   :class :__class_symbol__
                   :shift
                   (fn [this x-inc y-inc]
                     (make Point (+ (:x this) x-inc)
                                 (+ (:y this) y-inc)))}}))
