'use client'

import React, { useState } from "react"

export default function Home() {
  // change Backend URL accordingly
  const API_URL = `http://101.132.164.5:8080/search?keyword=`;

  const [foodData, setFoodData] = useState([]);
  const [query, setQuery] = useState('');
  const [firsttime, setFirsttime] = useState(true);
  // used for filter data
  const [tempData, setTempData] = useState([]);

  async function handleClick() {
    const url = `${API_URL}${query}`;
    let result = await (await fetch(url)).json();
    setFoodData(result);
    setTempData(result);
    setFirsttime(false);
  }

  // filter location according to input words
  function filterLocation(event: { target: { value: string; }; }) {
    setFoodData(tempData.filter(item => (item['location'] as String).toLowerCase().includes(event.target.value.toLowerCase())));
  }

  return (
    <div className="flex flex-col min-h-screen">
      <main className="flex flex-col items-center flex-1 px-4 sm:px-20 text-center">
        <div className="flex flex-wrap items-center justify-around my-8 sm:w-full bg-white rounded-md shadow-xl h-full border border-gray-100">
          <div className="mx-4 w-full">
            <form className="relative my-4" action={handleClick}>
              <input
                type="text"
                name="keyword"
                id="keyword"
                value={query}
                onChange={(event) => setQuery(event.target.value)}
                placeholder="eat something like sandwich, salad or others?"
                maxLength={20}
                required
                className="px-3 py-3 mt-1 text-lg block w-full border border-gray-200 rounded-md text-gray-900 placeholder-gray-400 focus:outline-none focus:ring focus:ring-blue-300"
              />
              <button
                className="flex items-center justify-center absolute right-2 top-2 h-10 border rounded-md w-20 bg-sky-500 hover:bg-sky-700 font-medium"
                type="submit"
              >
                Search
              </button>
            </form>

            <div>
              {
                foodData.length > 0 && (
                  <div>
                    <div className="text-lg text-left text-slate-500">Total result: {foodData.length} <input name="fileterLoc" type="text" onChange={filterLocation} className="rounded border-2 border-black px-1" placeholder="do more location filter"></input></div>
                    <table className="w-full text-left table-fixed text-wrap">
                      <thead className="bg-slate-50 border-b border-slate-200">
                        <tr>
                          <th className="py-3 text font-medium text-slate-900">No.</th>
                          <th className="px-6 py-3 text font-medium text-slate-900">Location </th>
                          <th className="px-6 py-3 text font-medium text-slate-900">Adress</th>
                          <th className="px-6 py-3 text font-medium text-slate-900">Food Items</th>
                        </tr>
                      </thead>
                      <tbody>
                        {foodData.map((item, index) => (
                          <tr key={index} className="odd:bg-white even:bg-slate-50">
                            <td className="px-6 py-4 whitespace-nowrap text font-medium text-slate-900 text-wrap">{index}</td>
                            <td className="px-6 py-4 whitespace-nowrap text font-medium text-slate-900 text-wrap">{item['location']}</td>
                            <td className="px-6 py-4 whitespace-nowrap text font-medium text-slate-900 text-wrap">{item["address"]}</td>
                            <td className="px-6 py-4 whitespace-nowrap text font-medium text-slate-900 text-wrap">{item["foodItems"]}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
              <div>
                {
                  // don't show tip when page load first time
                  (foodData.length == 0 && !firsttime) && (
                    <p className="text-lg text-slate-500">No matched food found. try other foods</p>
                  )
                }
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
