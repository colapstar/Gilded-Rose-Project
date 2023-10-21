package com.gildedrose;

// GildedRose inventory management system.
class GildedRose {
  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  // Update quality and sell-in values for all items in the inventory.
  public void updateQuality() {
    for (Item item : items) {
      updateItemQuality(item);
      updateItemSellIn(item);
    }
  }

  // Adjust the quality of an item based on its type and sell-in value.
  private void updateItemQuality(Item item) {
    switch (item.name) {
      case "Aged Brie":
        increaseQuality(item, 1);
        if (item.sellIn <= 0) {
          increaseQuality(item, 1);
        }
        break;
      case "Backstage passes to a TAFKAL80ETC concert":
        if (item.sellIn > 10) {
          increaseQuality(item, 1);
        } else if (item.sellIn > 5) {
          increaseQuality(item, 2);
        } else if (item.sellIn > 0) {
          increaseQuality(item, 3);
        } else {
          item.quality = 0;
        }
        break;
      case "Sulfuras, Hand of Ragnaros":
        break;
      case "Conjured":
        decreaseQuality(item, 2);
        if (item.sellIn <= 0) {
          decreaseQuality(item, 2);
        }
        break;
      default:
        decreaseQuality(item, 1);
        if (item.sellIn <= 0) {
          decreaseQuality(item, 1);
        }
    }
  }

  // Decrease the sell-in value for an item.
  private void updateItemSellIn(Item item) {
    if (!"Sulfuras, Hand of Ragnaros".equals(item.name)) {
      item.sellIn -= 1;
    }
  }

  // Increase quality for certain items, but not beyond 50.
  private void increaseQuality(Item item, int amount) {
    item.quality = Math.min(50, item.quality + amount);
  }

  // Decrease quality, but not below 0.
  private void decreaseQuality(Item item, int amount) {
    item.quality = Math.max(0, item.quality - amount);
  }
}
