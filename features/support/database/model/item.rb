class Item < ActiveRecord::Base
  self.table_name = 'item'
  has_many :tags

  def self.insert(item)
    item['username'] = 'ATDD_USER' if item['username'].blank?
    self.create(item)
  end

  def self.cleanup
    self.delete_all
  end
end