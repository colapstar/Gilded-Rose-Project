package com.gildedrose;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

  @Test
  void testName() {
    Item element = new Item("foo", 0, 0);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals("foo", element.name, "the name changed"); // The item name should remain unchanged after updating quality
  }

  @Test
  void testQualityDecreaseForNormalItem() {
    Item element = new Item("normal", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(19, element.quality); // Quality of a normal item should decrease by 1
    assertEquals(9, element.sellIn); // Sell-in value should decrease by 1
  }

  @Test
  void testQualityDecreaseTwiceAfterSellIn() {
    Item element = new Item("normal", 0, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(18, element.quality); // Quality should decrease by 2 after the sell-in date
  }

  @Test
  void testAgedBrieQualityIncrease() {
    Item element = new Item("Aged Brie", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(21, element.quality); // Quality of "Aged Brie" should increase by 1
  }

  @Test
  void testQualityNeverNegative() {
    Item element = new Item("normal", 10, 0);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(0, element.quality); // Quality should never go below 0
  }

  @Test
  void testQualityNeverMoreThan50() {
    Item element = new Item("Aged Brie", 10, 50);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(50, element.quality); // Quality should never exceed 50
  }

  @Test
  void testSulfurasNeverChanges() {
    Item element = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(80, element.quality); // Quality of "Sulfuras" should remain unchanged
    assertEquals(10, element.sellIn); // Sell-in value of "Sulfuras" should remain unchanged
  }

  @Test
  void testBackstagePassesQualityIncreaseBy2() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(22, element.quality); // Quality should increase by 2 when there are 10 days or less
  }

  @Test
  void testBackstagePassesQualityIncreaseBy3() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(23, element.quality); // Quality should increase by 3 when there are 5 days or less
  }

  @Test
  void testBackstagePassesQualityDropsTo0AfterConcert() {
    Item element = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(0, element.quality); // Quality should drop to 0 after the concert date
  }

  @Test
  void testAgedBrieQualityIncreaseAfterSellIn() {
    Item element = new Item("Aged Brie", -1, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(22, element.quality); // Quality of "Aged Brie" should increase by 2 after the sell-in date
  }

  @Test
  void testItemToString() {
    Item element = new Item("Aged Brie", 10, 20);
    String expectedString = "Aged Brie, 10, 20";
    assertEquals(expectedString, element.toString()); // The toString method should return the item's details in the specified format
  }

  /* Starting from here are the additional tests to improve coverage mutations from the initial 81% to 100% */
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

  @Test
  public void whenUpdateQualityAndQualityIs49_thenQualityDoesNotExceed50() {
    Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49)};
    GildedRose app = new GildedRose(items);
    app.updateQuality();
    assertTrue(app.items[0].quality <= 50); // Even if quality is at 49, updating quality should not exceed 50
  }

  @Test
  public void whenUpdateQualityAndQualityIs49_thenQualityDoesNotExceed50v2() {
    Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49)};
    GildedRose app = new GildedRose(items);
    app.updateQuality();
    assertTrue(app.items[0].quality <= 50); // Even with 5 days left, updating quality from 49 should not exceed 50
  }

  @Test
  void testQualityWhenSellInIsZero() {
    Item element = new Item("normal", 1, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(19, element.quality); // Quality should decrease by 1 when there's 1 day left to sell
  }

  /* Starting from here are the additional tests for the Conjured item */
  @Test
  void testConjuredQualityDecreaseBeforeSellIn() {
    Item element = new Item("Conjured", 10, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(18, element.quality); //Quality should decrease by 2 before sellIn reaches 0
    assertEquals(9, element.sellIn); //sellIn should decrease by 1
  }

  @Test
  void testConjuredQualityDecreaseAfterSellIn() {
    Item element = new Item("Conjured", 0, 20);
    GildedRose app = new GildedRose(new Item[]{element});
    app.updateQuality();
    assertEquals(16, element.quality); //Quality should decrease by 4 after sellIn is 0 or less
  }
}




