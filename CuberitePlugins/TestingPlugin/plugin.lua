function Initialize(Plugin)
	Plugin:SetName("Kry's Test Plugin")
	Plugin:SetVersion(0.0)
	
	local aVector = Vector3f(math.random(), math.random(), math.random())
	
	local bVector = Vector3f(50, 50, 50)
	
	local cVector = aVector * bVector
	
	print(cVector)
	
	cPluginManager:AddHook(cPluginManager.HOOK_PLAYER_JOINED, OnPlayerJoined)
	cPluginManager:AddHook(cPluginManager.HOOK_PLAYER_LEFT_CLICK, OnPlayerLeftClick)
	cPluginManager:AddHook(cPluginManager.HOOK_TICK, OnTick)
	
	
	return true
end

function OnTick(Delta)
	-- print(Delta)
end

function OnPlayerJoined(Player)
	print(Player:GetName() .. " joined the game")
end

function OnPlayerLeftClick(Player, BlockPos, Status)
	print(Player:GetName() .. " left clicked at " .. BlockPos .. " with status " .. Status)
	return false
end