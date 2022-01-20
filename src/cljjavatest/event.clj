(ns cljjavatest.event
  (:require [cheshire.core :as json]))

;;; Class definitions

(gen-class
  :name cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :methods [[getTimestamp [] long]
            [getState [] String]])

(gen-class
  :name cljjavatest.event.DrakeEventWorkflowBegin
  :extends cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :methods [[getSteps [] String]]
  :state state
  :init init)

(gen-class
  :name cljjavatest.event.DrakeEventWorkflowEnd
  :extends cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :state state
  :init init)

(gen-class
  :name cljjavatest.event.DrakeEventStepBegin
  :extends cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :methods [[getStep [] String]]
  :state state
  :init init)

(gen-class
  :name cljjavatest.event.DrakeEventStepEnd
  :extends cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :methods [[getStep [] String]]
  :state state
  :init init)

(gen-class
  :name cljjavatest.event.DrakeEventStepError
  :extends cljjavatest.event.DrakeEvent
  :prefix DrakeEvent-
  :methods [[getStep [] String]
            [getStepError [] String]]
  :state state
  :init init)

;;; Class methods

(defn DrakeEvent-init
  []
  [[] (atom {})])

(defn DrakeEvent-getTimestamp
  [this]
  (:timestamp @(.state this)) )

(defn DrakeEvent-getState
  [this]
  (json/generate-string @(.state this)))

(defn DrakeEvent-getSteps
  [this]
  (json/generate-string (:steps @(.state this))))

(defn DrakeEvent-getStep
  [this]
  (json/generate-string (:step @(.state this))))

(defn DrakeEvent-getStepError
  [this]
  (json/generate-string (:error @(.state this))))

;;; Class "constructors"

(defn setup-event-state
  [event state-props]
  (let [state (.state event)]
    (reset! state
            (assoc state-props :timestamp (System/currentTimeMillis))))
  event)

(defn EventWorkflowBegin
  "Constructor function for EventWorkflowBegin class.
  Takes a JSON array of step hashes."
  [steps]
  (setup-event-state (cljjavatest.event.DrakeEventWorkflowBegin.)
                     {:type "workflow-begin"
                      :steps steps}))

(defn EventWorkflowEnd
  "Constructor function for EventWorkflowEnd class."
  []
  (setup-event-state (cljjavatest.event.DrakeEventWorkflowEnd.)
                     {:type "workflow-end"}))

(defn EventStepBegin
  "Constructor function for EventStepBegin class.
  Takes a JSON hash with all the step info."
  [step]
  (setup-event-state (cljjavatest.event.DrakeEventStepBegin.)
                     {:type "step-begin"
                      :step step}))

(defn EventStepEnd
  "Constructor function for EventStepEnd class.
  Takes a JSON hash with all the step info."
  [step]
  (setup-event-state (cljjavatest.event.DrakeEventStepEnd.)
                     {:type "step-end"
                      :step step}))

(defn EventStepError
  "Constructor function for EventStepError class.
  Takes a JSON hash with all the step info and a
  Throwable that represents the error."
  [step ^Throwable error]
  (setup-event-state (cljjavatest.event.DrakeEventStepError.)
                     {:type "step-error"
                      :step step
                      :error (.toString error)}))
