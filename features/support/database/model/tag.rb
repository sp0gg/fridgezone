class Tag < ActiveRecord::Base
  self.table_name = 'tag'
  belongs_to :item

  def self.cleanup
    self.delete_all
  end
end