function Initialize(Plugin)
	-- Plugin:SetName("CoreAntiCheat")
	
	
	RegisterEvent(PlayerLoginEvent, OnPlayerJoined)
	
	return true
end

function OnPlayerJoined(aEvent)
	print("Player joined")
end





function ToString(Table, Indent)
	local String = ""
	if not(Indent) then
		Indent = 1
	end
	if type(Table) == "table" then
		String = String .. "{" .. "\n"
		for Key, _ in pairs(Table) do
			local Value = rawget(Table, Key)
			local KeyString = ""
			if type(Key) == "string" then
				KeyString = '["' .. tostring(Key) .. '"]'
			else
				KeyString = '[' .. tostring(Key) .. ']'
			end
			if type(Value) == "table" then
				String = String .. string.rep("\t", Indent) .. KeyString .. " = " .. ToString(Value, Indent + 1) .. ",\n"
			else
				String = String .. string.rep("\t", Indent) .. KeyString .. " = [[" .. tostring(Value) .. "]],\n"
			end
		end
		String = String .. string.rep("\t", Indent - 1) .. "}"
	else
		return tostring(Table)
	end
	return String
end

function table.contains(table, element)
  for _, value in pairs(table) do
    if value == element then
      return true
    end
  end
  return false
end