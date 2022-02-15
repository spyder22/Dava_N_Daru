
export const cartItemCount = (state) => {
  return state.cart.length
}

export const products = (state) => {
  return state.products
}
export const cartTotalPrice = (state) => {
  let total = 0
  state.cart.forEach(item => {
    total += item.product.merchantList[0].price * item.quantity
  })
  return total
}
