class Inventory
  include PageObject

  page_url 'http://localhost:9510/Fridgezone/'

  button(:filter_favorites, :id => 'filterFavorites')
  button(:filter_shopping, :id => 'filterShopping')
  text_field(:tag_filter, :id => 'tagFilter')

  div(:items, :class => 'ui-grid-canvas', :index => 1)
  div(:item_selectors, :class => 'ui-grid-canvas', :index => 0)
  div(:item_tags, :id => 'itemTags')


  text_field(:item_add_name, :id => 'itemAddName')
  select(:item_add_stock_level, :id => 'itemAddStockLevel')
  checkbox(:item_add_tag_favorite, :id => 'itemAddTagFavorite')
  checkbox(:item_add_tag_shopping, :id => 'itemAddTagShopping')

  button(:add_new_tag, :id => 'itemAddTag')
  button(:save_new_tag, :id => 'itemSaveTag')
  text_field(:new_tag_name, :id => 'itemAddTagName')
  unordered_list(:custom_tags, :id => 'customTags')

  form(:selected_cell_input_form, :name => 'inputForm')
  form(:itemForm, :name => 'itemForm')
  button(:save_item, :id => 'saveItem')
  button(:add_item, :id => 'addItem')

  div(:alerts, :id => 'alerts')

  def item_selection_buttons
    self.item_selectors_element.div_elements(:class => 'ui-grid-selection-row-header-buttons')
  end

  def select_item(name)
    item_row = find_item_row(name)
    selector_index = self.item_rows.find_index(item_row)
    self.item_selection_buttons[selector_index].click
  end

  def item_rows
    self.items_element.div_elements(:class => 'ui-grid-row')
  end

  def find_item_row(text)
    item_row = self.item_rows.find() { |row| row.text.include?(text) }
    raise "Could not find row containing '#{text}'" if item_row.nil?
    item_row
  end

  def get_item_cells(name)
    find_item_row(name).div_elements(:class => 'ui-grid-cell-contents')
  end

  def get_display_item(name)
    item_cells = get_item_cells(name)
    display_item = Hash.new
    display_item['name'] = item_cells[0].text
    display_item['stock_level'] = item_cells[1].text
    display_item['tags'] = item_cells[2].text
    display_item
  end

  def remove_custom_tag(tag)
    tag_element = self.custom_tags_element.find(){|row| row.text.include?(tag)}
    # tag_element = self.custom_tags_element[0]
    # puts "tag is #{tag_element.button_elements[0]}"
    tag_element.button_elements[0].click
  end

  def wait_for_text(text)
     self.wait_until(5, "Could not find text: #{text}") do
       self.text.include? text
     end
  end

end