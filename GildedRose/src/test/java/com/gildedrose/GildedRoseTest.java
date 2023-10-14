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

  @Test
  void testQualityNeverMoreThan50() {
    Item element = new Item("Aged Brie", 10, 50);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(50, element.quality);
  }

  @Test
  void testSulfurasNeverChanges() {
    Item element = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(80, element.quality);
    assertEquals(10, element.sellIn);
  }

  @Test
  void testBackstagePassesQualityIncreaseBy2() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(22, element.quality);
  }

  @Test
  void testBackstagePassesQualityIncreaseBy3() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(23, element.quality);
  }

  @Test
  void testBackstagePassesQualityDropsTo0AfterConcert() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(0, element.quality);
  }

  @Test
  void testAgedBrieQualityIncreaseAfterSellIn() {
    Item element = new Item("Aged Brie", -1, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(22, element.quality); // Quality should increase by 2 after sell-by date
  }

  @Test
  void testItemToString() {
    Item element = new Item("Aged Brie", 10, 20);
    String expectedString = "Aged Brie, 10, 20";
    assertEquals(expectedString, element.toString());
  }

  @Test
  void testBackstagePassesQualityIncreaseBoundary11Days() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(21, element.quality); // Quality should increase by 1 when there are 11 days left
  }

  @Test
  void testBackstagePassesQualityIncreaseBoundary6Days() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(22, element.quality); // Quality should increase by 2 when there are 6 days left
  }

  @Test
  void testNormalItemQualityDecreaseAfterSellInBoundary() {
    Item element = new Item("normal", -1, 1);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(0, element.quality); // Quality should decrease to 0
  }

  @Test
  void testAgedBrieQualityIncreaseBoundary() {
    Item element = new Item("Aged Brie", -1, 49);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(50, element.quality); // Quality should increase to 50 and not exceed it
  }
}



