package com.example.demo.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.DMPDao;
import com.example.demo.vo.Pet;

@Service
public class DMPService {

	@Autowired
	private DMPDao dmpDao;
	
	public void lastPetIndex() {
		System.out.println(dmpDao.lastPetIndex());
	}
	
	public String queryAllPet() throws Exception {
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);

		
		Path networkConfigPath = Paths.get("resource", "connection.json");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);
		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
			byte[] result;
			result = contract.evaluateTransaction("queryAllPets");
			System.out.println(result);
			return new String(result);
		}
	}
	
	public List<String> querySubmit(String id) throws Exception {
		List<String> keyList = dmpDao.petList(id);
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		Path networkConfigPath = Paths.get("resource", "connection.json");
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);
		List<String> petList = new ArrayList<>();
		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
			for (String key : keyList) {
				byte[] result;
				result = contract.evaluateTransaction("queryPet", key);
				String resultStr = "{\"info\":" + new String(result) + "}";
				System.out.println(resultStr);
				petList.add(resultStr);
			}
			

			return petList;
		}
	}

	public String createPet(Pet pet) throws Exception {
		//db
		int petKey= Integer.parseInt(dmpDao.lastPetIndex())+1;
		System.out.println(petKey);
		String petKeyStr = "PET"+Integer.toString(petKey);
		System.out.println(dmpDao.createPet(pet.getPet_owner()));
		
		//hyperledger
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		Path networkConfigPath = Paths.get("resource", "connection.json");
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);
		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
			
			contract.submitTransaction("createPet", petKeyStr,pet.getPet_name(), pet.getPet_owner(), pet.getPet_gender(),
					pet.getPet_breed(), pet.getPet_birth());
			return "Ok";
		}
	}
}
