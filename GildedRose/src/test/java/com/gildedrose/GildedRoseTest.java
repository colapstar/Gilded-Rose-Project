package com.gildedrose;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

  @Test
  @DisplayName("Test that the name is unchanged")
  void testName() {
    Item element = new Item("foo", 0, 0);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals("foo", element.name, "the name changed");
  }

  @Test
  void testQualityDecreaseForNormalItem() {
    Item element = new Item("normal", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(19, element.quality);
    assertEquals(9, element.sellIn);
  }

  @Test
  void testQualityDecreaseTwiceAfterSellIn() {
    Item element = new Item("normal", 0, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(18, element.quality);
  }

  @Test
  void testAgedBrieQualityIncrease() {
    Item element = new Item("Aged Brie", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(21, element.quality);
  }

  @Test
  void testQualityNeverNegative() {
    Item element = new Item("normal", 10, 0);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(0, element.quality);
  }


}



