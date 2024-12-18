package org.ng.fhb.model;

import java.util.HashMap;
import java.util.Map;

    public class BotSupplyData {
        private Map<String, ItemData> items;
        public Map<String, ItemData> getItems() {
            return items;
        }

        public void setItems(Map<String, ItemData> items) {
            this.items = items;
        }

        public Map<Object, Object> getContributions() {//
            Map<Object, Object> contributions = new HashMap<Object, Object>();
            return contributions;
        }




        public static class ItemData {
            private int quantity;
            private int target;

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getTarget() {
                return target;
            }

            public void setTarget(int target) {
                this.target = target;
            }
        }
    }

