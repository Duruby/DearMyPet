package com.example.demo.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.springframework.stereotype.Service;

import com.example.demo.vo.Pet;

@Service
public class DMPService {

	public DMPService() {
	}
	
	public String queryAllPet() throws Exception {
		// TODO Auto-generated method stub

		// Load a file system based wallet for managing identities.
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);

		// load a CCP
		Path networkConfigPath = Paths.get("resource", "connection.json");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);

		// create a gateway connection
		try (Gateway gateway = builder.connect()) {

			// get the network and contract
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
//
			byte[] result;
			result = contract.evaluateTransaction("queryAllPets");
			System.out.println(result);
			return new String(result);
		}
	}
	
	public String querySubmit(String petNumber) throws Exception {
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		Path networkConfigPath = Paths.get("resource", "connection.json");
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);
		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
			byte[] result;
			result = contract.evaluateTransaction("queryPet", petNumber);
			System.out.println(result);

			return new String(result);
		}
	}

	public String createPet(Pet pet) throws Exception {
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);
		Path networkConfigPath = Paths.get("resource", "connection.json");

		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);
		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");

			contract.submitTransaction("createPet", "PET11",pet.getPet_name(), pet.getPet_owner(), pet.getPet_gender(),
					pet.getPet_breed(), pet.getPet_birth());
			return "Ok";
		}
	}
	
	
	public String transferSubmit(String car_id2, String car_owner2) throws Exception {
		Path walletPath = Paths.get("wallet");
		Wallet wallet = Wallet.createFileSystemWallet(walletPath);

		// load a CCP
		Path networkConfigPath = Paths.get("resource", "connection.json");
		Gateway.Builder builder = Gateway.createBuilder();
		builder.identity(wallet, "user1").networkConfig(networkConfigPath).discovery(false);

		try (Gateway gateway = builder.connect()) {
			Network network = gateway.getNetwork("mychannel");
			Contract contract = network.getContract("dmp");
			contract.submitTransaction("changePetOwner", car_id2, car_owner2);

			return "Ok";
		}
	}
}
