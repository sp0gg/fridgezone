Given(/^I am logged in to Fridgezone$/) do
  visit(Login)
  on(Login) do |page|
    page.username = 'ATDD_USER'
    page.password = 'OUf?lJ?of|%Cz8F&N.rd'
    page.login
  end
end

When(/^I am viewing the fridgezone page$/) do
  visit(Inventory)
end

Given(/^I have listed the following items in my fridge:$/) do |items|
  items.hashes.each do |item|
    Item.insert(item)
  end
end

When(/^I have added the following items:$/) do |items|
  # table is a table.hashes.keys # => [:Name, :Quantity, :Units]
  on(Inventory) do |page|
    items.hashes.each do |item|
      page.add_item
      page.item_add_name = item['Name']
      page.item_add_stock_level = item['Stock Level']
      page.save_item
    end
  end
end

Then(/^I should see the following items on the page:$/) do |items|
  # table is a table.hashes.keys # => [:Name, :Stock Level, :Optimal Quantity]

  on(Inventory) do |page|
    page.wait_for_text(items.hashes.last['Name'])

    items.hashes.each do |item|
      display_item = page.get_display_item(item['Name'])

      display_name = display_item['name']
      display_stock_level = display_item['stock_level']

      display_name.should eql(item['Name'])
      display_stock_level.should eql(item['Stock Level'])
    end
  end
end

And(/^I have updated the item "([^"]*)" with the following values:$/) do |item_name_to_update, new_item|
  # table is a table.hashes.keys # => [:Name, :Stock Level, :Optimal Quantity]
  on(Inventory) do |page|

    item = new_item.hashes.first

    page.select_item(item_name_to_update)
    page.item_add_name = item['Name']
    page.item_add_stock_level = item['Stock Level']
    page.save_item

  end
end

And(/^I should NOT see the following items on the page:$/) do |items|
  # table is a table.hashes.keys # => [:Name, :Stock Level, :Optimal Quantity]
  on(Inventory) do |page|

    items.hashes.each do |item|
      found = false
      begin
        display_item = page.get_display_item(item['Name'])
      rescue => e
        e.message.should eql("Could not find row containing '#{item['Name']}'")
        found = true
      end
      raise "Should not have found '#{item['Name']}' in the list" if !found

    end
  end
end

And(/^I have filtered the view by favorites$/) do
  on(Inventory).filter_favorites
end

And(/^I have already created the following tag associations:$/) do |tags|
  # table is a table.hashes.keys # => [:item, :tag]
  tags.hashes.each do |tag|
    @item = Item.find_by(name:tag['item'])
    @item.tags.create(name:tag['name'])
  end
end

And(/^I have marked the item "([^"]*)" as a favorite item$/) do |item_name|
  on(Inventory) do |page|
    page.select_item(item_name)
    page.check_item_add_tag_favorite
    page.save_item
  end
end

Then(/^the item "([^"]*)" should be marked as a favorite item$/) do |item_name|
  on(Inventory) do |page|
    page.wait_for_text(item_name)
    display_item = page.get_display_item(item_name)
    display_tags = display_item['tags']

    display_tags.should include('favorite')
  end
end

And(/^I have filtered the view by shopping$/) do
  on(Inventory).filter_shopping
end

And(/^I have marked the item "([^"]*)" as a shopping item$/) do |item_name|
  on(Inventory) do |page|
    page.select_item(item_name)
    page.check_item_add_tag_shopping
    page.save_item
  end
end

Then(/^the item "([^"]*)" should be marked as a shopping item$/) do |item_name|
  on(Inventory) do |page|
    page.wait_for_text(item_name)
    display_item = page.get_display_item(item_name)
    display_tags = display_item['tags']

    display_tags.should include('shopping')
  end
end

And(/^I view the item "([^"]*)"$/) do |item_name|
  on(Inventory).select_item(item_name)
end

Then(/^the custom tag "([^"]*)" should be present$/) do |tag|
  on(Inventory).custom_tags.should include(tag)
end

And(/^I mark the item "([^"]*)" with new custom tag "([^"]*)"$/) do |item_name, tag|
  on(Inventory) do |page|
    page.select_item(item_name)
    page.new_tag_name = tag
    page.save_new_tag
    page.save_item
  end
end

Then(/^the item "([^"]*)" should be marked with tag "([^"]*)"$/) do |item_name, tag|
  on(Inventory) do |page|
    page.wait_for_text(item_name)
    display_item = page.get_display_item(item_name)
    display_tags = display_item['tags']

    display_tags.should include(tag)
  end
end

When(/^I remove the custom tag "([^"]*)" from the item "([^"]*)"$/) do |tag, item_name|
  on(Inventory) do |page|
      page.select_item(item_name)
      page.remove_custom_tag(tag)
      page.save_item
    end
end

Then(/^the item "([^"]*)" should NOT be marked with tag "([^"]*)"$/) do |item_name, tag|
  on(Inventory) do |page|
    page.wait_for_text(item_name)
    display_item = page.get_display_item(item_name)
    display_tags = display_item['tags']

    display_tags.should_not include(tag)
  end
end

When(/^I filter by custom tag "([^"]*)"$/) do |tag|
  on(Inventory).tag_filter = tag
end

Then(/^I should see an alert with text: "([^"]*)"$/) do |alert_text|
  on(Inventory) do |page|
    page.alerts.should include(alert_text)
  end
end